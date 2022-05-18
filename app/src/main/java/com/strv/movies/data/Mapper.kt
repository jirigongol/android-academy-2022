package com.strv.movies.data

interface Mapper<I, O> {
    fun map(from: I): O
}
