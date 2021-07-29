package com.github.marfikus.jokeapp

import androidx.annotation.DrawableRes

abstract class Joke(private val text: String, private val punchline: String) {

    fun getJokeUi() = "$text\n$punchline"

    @DrawableRes
    abstract fun getIconResId(): Int
}

class BaseJoke(text: String, punchline: String) : Joke(text, punchline) {
    override fun getIconResId() = R.drawable.baseline_favorite_border_24
}

class FavoriteJoke(text: String, punchline: String) : Joke(text, punchline) {
    override fun getIconResId() = R.drawable.baseline_favorite_24
}

class FailedJoke(text: String) : Joke(text, "") {
    override fun getIconResId() = 0
}