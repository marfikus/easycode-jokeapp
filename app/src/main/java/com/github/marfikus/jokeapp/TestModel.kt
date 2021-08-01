package com.github.marfikus.jokeapp

class TestModel(resourceManager: ResourceManager) : Model {

    private var callback: JokeCallback? = null
    private var count = 0
    private val noConnection = NoConnection(resourceManager)
    private val serviceUnavailable = ServiceUnavailable(resourceManager)

    override fun getJoke() {
        Thread {
            Thread.sleep(1000)
            when (count) {
                0 -> callback?.provide(BaseJokeUiModel("testText", "testPunchline"))
                1 -> callback?.provide(FavoriteJokeUiModel("testText Favorite", "testPunchline Favorite"))
                2 -> callback?.provide(FailedJokeUiModel(serviceUnavailable.getMessage()))
            }
            count++
            if (count == 3) count = 0
        }.start()
    }

    override fun init(callback: JokeCallback) {
        this.callback = callback
    }

    override fun clear() {
        callback = null
    }

    override fun changeJokeStatus(callback: JokeCallback) {
        TODO("Not yet implemented")
    }

    override fun chooseDataSource(cached: Boolean) {
        TODO("Not yet implemented")
    }
}