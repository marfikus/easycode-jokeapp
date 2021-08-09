package com.github.marfikus.jokeapp.domain

import com.github.marfikus.jokeapp.*
import com.github.marfikus.jokeapp.core.Mapper
import com.github.marfikus.jokeapp.presentation.BaseJokeUiModel
import com.github.marfikus.jokeapp.presentation.FailedJokeUiModel
import com.github.marfikus.jokeapp.presentation.FavoriteJokeUiModel
import com.github.marfikus.jokeapp.presentation.JokeUiModel

sealed class Joke : Mapper<JokeUiModel> {
    class Success(
            private val text: String,
            private val punchline: String,
            private val favorite: Boolean
    ) : Joke() {
        override fun to(): JokeUiModel {
            return if (favorite) {
                FavoriteJokeUiModel(text, punchline)
            } else {
                BaseJokeUiModel(text, punchline)
            }
        }
    }

    class Failed(private val failure: JokeFailure) : Joke() {
        override fun to(): JokeUiModel {
            return FailedJokeUiModel(failure.getMessage())
        }

    }
}