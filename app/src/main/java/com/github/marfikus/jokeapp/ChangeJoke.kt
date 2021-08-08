package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.JokeDataModel
import java.lang.IllegalStateException

interface ChangeJoke {
    suspend fun change(changeJokeStatus: ChangeJokeStatus): JokeDataModel

    class Empty : ChangeJoke {
        override suspend fun change(changeJokeStatus: ChangeJokeStatus): JokeDataModel {
            throw IllegalStateException("empty change joke called")
        }

    }
}