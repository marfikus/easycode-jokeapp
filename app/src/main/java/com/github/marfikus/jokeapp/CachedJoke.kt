package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.JokeDataModel

interface CachedJoke : ChangeJoke {
    fun saveJoke(joke: JokeDataModel)
    fun clear()
}