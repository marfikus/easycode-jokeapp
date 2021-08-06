package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.Result

interface JokeDataFetcher<S, E> {
    suspend fun getJoke(): Result<S, E>
}