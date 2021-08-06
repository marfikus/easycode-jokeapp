package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.JokeDataModel

interface ChangeJokeStatus {
    suspend fun addOrRemove(id: Int, joke: Joke): JokeDataModel
}