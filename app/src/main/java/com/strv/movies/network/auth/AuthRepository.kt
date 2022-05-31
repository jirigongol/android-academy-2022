package com.strv.movies.network.auth

import android.util.Log
import com.strv.movies.database.AuthDataStore
import com.strv.movies.extension.Either
import com.strv.movies.model.CreateSessionBody
import com.strv.movies.model.DeleteSessionBody
import com.strv.movies.model.ValidateRequestTokenBody
import javax.inject.Inject
import javax.inject.Singleton

enum class AuthError{
    INVALID_CREDENTIALS, NETWORK_ERROR
}

@Singleton
class AuthRepository @Inject constructor(
    private val authDataStore: AuthDataStore,
    private val authApi: AuthApi
){
    suspend fun logIn(username: String, password: String): Either<AuthError, Boolean>{
        return try {
            val requestToken = authApi.getRequestToken()
            if(requestToken.isSuccess.not()) {
                Log.d("AUTH", "Auth Error - Failed to get request token")
                return Either.Error(AuthError.NETWORK_ERROR)
            }
            val validationResponse= authApi.validateRequestToken(ValidateRequestTokenBody(
                username = username,
                password = password,
                requestToken = requestToken.token
            ))
            if(validationResponse.isSuccess.not()) {
                Log.d("AUTH", "Auth Error - Failed to validate credentials")
                return Either.Error(AuthError.INVALID_CREDENTIALS)
            }
            val createSessionResponse = authApi.createSession(CreateSessionBody(validationResponse.requestToken))
            if(createSessionResponse.isSuccess.not()) {
                Log.d("AUTH", "Auth Error - Failed to create new session")
                return Either.Error(AuthError.NETWORK_ERROR)
            }
            authDataStore.updateSessionToken(createSessionResponse.sessionToken)
            Log.d("AUTH", "Auth successful")
            Either.Value(true)
        } catch (t: Throwable) {
            Log.d("AUTH", "Auth Error - Some network fail - ${t.localizedMessage}")
            Either.Error(AuthError.NETWORK_ERROR)
        }
    }

    suspend fun logOut(): Either<String, Boolean>{
        return try {
            authDataStore.sessionToken?.let {
                authApi.deleteSession(DeleteSessionBody(sessionToken = it))
            }
            authDataStore.deleteSessionToken()
            Either.Value(true)
        } catch (exception: Throwable){
            Either.Error(exception.localizedMessage ?: "Network error")
        }
    }
}