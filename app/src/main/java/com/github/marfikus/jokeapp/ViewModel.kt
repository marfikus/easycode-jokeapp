package com.github.marfikus.jokeapp

class ViewModel(private val model: Model<Any, Any>) {

    private var callback: TextCallback? = null

    fun init(callback: TextCallback) {
        this.callback = callback
        model.init(object : ResultCallback<Any, Any> {
            override fun provideSuccess(data: Any) {
                callback.provideText("success")
            }

            override fun provideError(error: Any) {
                callback.provideText("error")
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