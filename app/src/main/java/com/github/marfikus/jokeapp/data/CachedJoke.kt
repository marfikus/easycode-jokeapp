package com.github.marfikus.jokeapp.data

interface CachedJoke : ChangeJoke {
    fun saveJoke(joke: JokeDataModel)
    fun clear()
}