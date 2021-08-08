package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.JokeDataModel

interface ChangeJoke {
    suspend fun change(changeJokeStatus: ChangeJokeStatus): JokeDataModel

    class Empty : ChangeJoke {
        override suspend fun change(changeJokeStatus: ChangeJokeStatus): JokeDataModel {
            return JokeDataModel(0, "", "")
        }

    }
}