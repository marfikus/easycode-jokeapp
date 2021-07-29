package com.github.marfikus.jokeapp

import androidx.annotation.DrawableRes

class ViewModel(private val model: Model) {

    private var dataCallback: DataCallback? = null

    fun init(callback: DataCallback) {
        this.dataCallback = callback
        model.init(object : ResultCallback {
            override fun provideJoke(joke: Joke) {
                dataCallback?.let {
                    joke.map(it)
                }
            }
        })
    }

    fun getJoke() {
        model.getJoke()
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