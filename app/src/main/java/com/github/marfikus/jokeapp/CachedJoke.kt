package com.github.marfikus.jokeapp

interface CachedJoke {
    fun saveJoke(joke: Joke)
    fun clear()
    suspend fun changeStatus(changeJokeStatus: ChangeJokeStatus)
}