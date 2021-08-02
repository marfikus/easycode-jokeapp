package com.github.marfikus.jokeapp

interface CachedJoke : ChangeJoke {
    fun saveJoke(joke: Joke)
    fun clear()
}