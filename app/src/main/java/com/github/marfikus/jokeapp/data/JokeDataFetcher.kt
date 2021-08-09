package com.github.marfikus.jokeapp.data

interface JokeDataFetcher {
    suspend fun getJoke(): JokeDataModel
}