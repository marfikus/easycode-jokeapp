package com.github.marfikus.jokeapp

class BaseModel(
    private val cacheDataSource: CacheDataSource,
    private val cloudDataSource: CloudDataSource,
    private val resourceManager: ResourceManager
) : Model {

    private val noConnection by lazy { NoConnection(resourceManager) }
    private val serviceUnavailable by lazy { ServiceUnavailable(resourceManager) }
    private val noCachedJokes by lazy { NoCachedJokes(resourceManager) }

    private var jokeCallback: JokeCallback? = null
    private var cachedJoke: Joke? = null
    private var getJokeFromCache = false

    override fun getJoke() {
        if (getJokeFromCache) {
            cacheDataSource.getJoke(object : JokeCachedCallback {
                override fun provide(joke: Joke) {
                    cachedJoke = joke
                    jokeCallback?.provide(joke.toFavoriteJoke())
                }

                override fun fail() {
                    cachedJoke = null
                    jokeCallback?.provide(FailedJokeUiModel(noCachedJokes.getMessage()))
                }

            })
        } else {
            cloudDataSource.getJoke(object : JokeCloudCallback {
                override fun provide(joke: Joke) {
                    cachedJoke = joke
                    jokeCallback?.provide(joke.toBaseJoke())
                }

                override fun fail(error: ErrorType) {
                    cachedJoke = null
                    val failure = if (error == ErrorType.NO_CONNECTION) noConnection else serviceUnavailable
                    jokeCallback?.provide(FailedJokeUiModel(failure.getMessage()))
                }
            })
        }
    }

    override fun changeJokeStatus(jokeCallback: JokeCallback) {
        cachedJoke?.changeStatus(cacheDataSource, object : ChangeStatusCallback {
            override fun provide(joke: JokeUiModel) {
                jokeCallback.provide(joke)
            }

        })
    }

    override fun chooseDataSource(cached: Boolean) {
        getJokeFromCache = cached
    }

    override fun init(callback: JokeCallback) {
        this.jokeCallback = callback
    }

    override fun clear() {
        jokeCallback = null
    }
}