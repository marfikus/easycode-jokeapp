package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.JokeDataModel

interface ChangeJoke {
    suspend fun changeStatus(changeJokeStatus: ChangeJokeStatus): JokeDataModel?
}