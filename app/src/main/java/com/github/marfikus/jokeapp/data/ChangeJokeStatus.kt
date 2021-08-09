package com.github.marfikus.jokeapp.data

interface ChangeJokeStatus {
    suspend fun addOrRemove(id: Int, joke: JokeDataModel): JokeDataModel
}