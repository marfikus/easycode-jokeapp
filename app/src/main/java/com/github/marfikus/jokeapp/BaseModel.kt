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

    override suspend fun getJoke(): JokeUiModel {
        if (getJokeFromCache) {
            return when (val result = cacheDataSource.getJoke()) {
                is Result.Success<Joke> -> result.data.let {
                    cachedJoke = it
                    it.toFavoriteJoke()
                }
                is Result.Error -> {
                    cachedJoke = null
                    FailedJokeUiModel(noCachedJokes.getMessage())
                }
            }
        } else {
            return when (val result = cloudDataSource.getJoke()) {
                is Result.Success<JokeServerModel> -> {
                    result.data.toJoke().let {
                        cachedJoke = it
                        it.toBaseJoke()
                    }
                }
                is Result.Error<ErrorType> -> {
                    cachedJoke = null
                    val failure = if (result.exception == ErrorType.NO_CONNECTION)
                        noConnection
                    else
                        serviceUnavailable
                    FailedJokeUiModel(failure.getMessage())
                }
            }
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