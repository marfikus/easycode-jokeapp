package com.github.marfikus.jokeapp

class TestModel(resourceManager: ResourceManager) : Model {

    private var callback: ResultCallback? = null
    private var count = 0
    private val noConnection = NoConnection(resourceManager)
    private val serviceUnavailable = ServiceUnavailable(resourceManager)

    override fun getJoke() {
        Thread {
            Thread.sleep(1000)
            when (count) {
                0 -> callback?.provideJoke(BaseJoke("testText", "testPunchline"))
                1 -> callback?.provideJoke(FavoriteJoke("testText Favorite", "testPunchline Favorite"))
                2 -> callback?.provideJoke(FailedJoke(serviceUnavailable.getMessage()))
            }
            count++
            if (count == 3) count = 0
        }.start()
    }

    override fun init(callback: ResultCallback) {
        this.callback = callback
    }

    override fun clear() {
        callback = null
    }
}