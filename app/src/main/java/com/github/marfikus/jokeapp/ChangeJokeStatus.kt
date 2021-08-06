package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.JokeDataModel
import com.github.marfikus.jokeapp.domain.Joke

interface ChangeJokeStatus {
    suspend fun addOrRemove(id: Int, joke: Joke): JokeDataModel
}