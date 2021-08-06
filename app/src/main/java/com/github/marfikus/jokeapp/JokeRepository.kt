package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.JokeDataModel

interface JokeRepository {
    suspend fun getJoke(): JokeDataModel
    suspend fun changeJokeStatus(): JokeDataModel?
    fun chooseDataSource(cached: Boolean)
}