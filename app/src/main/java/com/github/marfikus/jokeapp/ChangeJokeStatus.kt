package com.github.marfikus.jokeapp

interface ChangeJokeStatus {
    suspend fun addOrRemove(id: Int, joke: Joke): JokeUiModel
}