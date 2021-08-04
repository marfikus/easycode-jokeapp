package com.github.marfikus.jokeapp

import androidx.annotation.DrawableRes

abstract class JokeUiModel(private val text: String, private val punchline: String) {
    protected open fun getText() = "$text\n$punchline"

    @DrawableRes
    protected abstract fun getIconResId(): Int

    fun show(communication: Communication) = communication.showData(Pair(getText(), getIconResId()))
}

class BaseJokeUiModel(text: String, punchline: String) : JokeUiModel(text, punchline) {
    override fun getIconResId() = R.drawable.baseline_favorite_border_24
}

class FavoriteJokeUiModel(text: String, punchline: String) : JokeUiModel(text, punchline) {
    override fun getIconResId() = R.drawable.baseline_favorite_24
}

class FailedJokeUiModel(private val text: String) : JokeUiModel(text, "") {
    override fun getText() = text
    override fun getIconResId() = 0
}