package com.github.marfikus.jokeapp

class ViewModel(private val model: Model<Joke, JokeFailure>) {

    private var callback: TextCallback? = null

    fun init(callback: TextCallback) {
        this.callback = callback
        model.init(object : ResultCallback<Joke, JokeFailure> {
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

interface TextCallback {

    fun provideText(text: String)
}