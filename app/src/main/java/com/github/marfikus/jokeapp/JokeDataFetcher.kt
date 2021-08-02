package com.github.marfikus.jokeapp

interface JokeDataFetcher<S, E> {
    suspend fun getJoke(): Result<S, E>
}