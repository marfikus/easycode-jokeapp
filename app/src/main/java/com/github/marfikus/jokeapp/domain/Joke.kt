package com.github.marfikus.jokeapp.domain

import com.github.marfikus.jokeapp.BaseJokeUiModel
import com.github.marfikus.jokeapp.FailedJokeUiModel
import com.github.marfikus.jokeapp.FavoriteJokeUiModel
import com.github.marfikus.jokeapp.JokeUiModel
import com.github.marfikus.jokeapp.core.Mapper

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

    class Failed(private val text: String) : Joke() {
        override fun to(): JokeUiModel {
            return FailedJokeUiModel(text)
        }

    }
}