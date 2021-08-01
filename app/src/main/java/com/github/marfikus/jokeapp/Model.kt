package com.github.marfikus.jokeapp

interface Model {

    fun getJoke()

    fun init(callback: JokeCallback)

    fun clear()

    fun changeJokeStatus(callback: JokeCallback)

    fun chooseDataSource(cached: Boolean)
}

interface JokeCallback {

    fun provide(joke: JokeUiModel)
}