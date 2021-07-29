package com.github.marfikus.jokeapp

import androidx.annotation.DrawableRes

class ViewModel(private val model: Model) {

    private var callback: DataCallback? = null

    fun init(callback: DataCallback) {
        this.callback = callback
        model.init(object : ResultCallback {
            override fun provideSuccess(data: Joke) {
                callback.provideText(data.getJokeUi())
            }

            override fun provideError(error: JokeFailure) {
                callback.provideText(error.getMessage())
            }
        })
    }

    fun getJoke() {
        model.getJoke()
    }

    fun clear() {
        callback = null
        model.clear()
    }
}

interface DataCallback {

    fun provideText(text: String)

    fun provideIconRes(@DrawableRes id:Int)
}