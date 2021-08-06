package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.JokeDataModel

interface JokeDataFetcher {
    suspend fun getJoke(): JokeDataModel
}