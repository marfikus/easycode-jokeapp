package com.github.marfikus.jokeapp

import androidx.annotation.DrawableRes

class ViewModel(private val model: Model) {

    private var dataCallback: DataCallback? = null

    private val jokeCallback = object : JokeCallback {
        override fun provide(joke: Joke) {
            dataCallback?.let {
                joke.map(it)
            }
        }
    }

    fun init(callback: DataCallback) {
        this.dataCallback = callback
        model.init(jokeCallback)
    }

    fun getJoke() {
        model.getJoke()
    }

    fun changeJokeStatus() {
        model.changeJokeStatus(jokeCallback)
    }

    fun clear() {
        dataCallback = null
        model.clear()
    }
}

interface DataCallback {

    fun provideText(text: String)

    fun provideIconRes(@DrawableRes id:Int)
}