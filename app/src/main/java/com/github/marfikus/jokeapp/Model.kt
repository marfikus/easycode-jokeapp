package com.github.marfikus.jokeapp

interface Model {

    suspend fun getJoke(): JokeUiModel

    fun init(callback: JokeCallback)

    fun clear()

    fun changeJokeStatus(callback: JokeCallback)

    fun chooseDataSource(cached: Boolean)
}

interface JokeCallback {

    fun provide(joke: JokeUiModel)
}