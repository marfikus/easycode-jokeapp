package com.github.marfikus.jokeapp

interface Model {

    suspend fun getJoke(): JokeUiModel

    fun init(callback: JokeCallback)

    fun clear()

    suspend fun changeJokeStatus(): JokeUiModel?

    fun chooseDataSource(cached: Boolean)
}

interface JokeCallback {

    fun provide(joke: JokeUiModel)
}