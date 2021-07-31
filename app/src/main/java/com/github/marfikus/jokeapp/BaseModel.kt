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

    private var cachedJokeServerModel: JokeServerModel? = null

    private var getJokeFromCache = false

    override fun getJoke() {
        if (getJokeFromCache) {
            cacheDataSource.getJoke(object : JokeCachedCallback {
                override fun provide(jokeModel: JokeModel) {
                    cachedJokeServerModel = jokeModel
                    jokeCallback?.provide(jokeModel.toFavoriteJoke())
                }

                override fun fail() {
                    cachedJokeServerModel = null
                    jokeCallback?.provide(FailedJoke(noCachedJokes.getMessage()))
                }

            })
        } else {
            cloudDataSource.getJoke(object : JokeCloudCallback {
                override fun provide(joke: JokeServerModel) {
                    cachedJokeServerModel = joke

                    // check on favorite (if joke already in favorites)
                    joke.checkExistInCache(cacheDataSource, object : CacheDataSourceCallback {
                        override fun onResult(exists: Boolean) {
                            if (exists) {
                                jokeCallback?.provide(joke.toFavoriteJoke())
                            } else {
                                jokeCallback?.provide(joke.toBaseJoke())
                            }
                        }
                    })
                }

                override fun fail(error: ErrorType) {
                    cachedJokeServerModel = null
                    val failure = if (error == ErrorType.NO_CONNECTION) noConnection else serviceUnavailable
                    jokeCallback?.provide(FailedJoke(failure.getMessage()))
                }
            })
        }
    }

    override fun changeJokeStatus(jokeCallback: JokeCallback) {
        cachedJokeServerModel?.changeStatus(cacheDataSource, object : ChangeStatusCallback {
            override fun provide(joke: Joke) {
                jokeCallback.provide(joke)
            }

        })
    }

    override fun chooseDataSource(cached: Boolean) {
        getJokeFromCache = cached
    }

    override fun init(callback: JokeCallback) {
        this.jokeCallback = callback
        cacheDataSource.init()
    }

    override fun clear() {
        jokeCallback = null
        cacheDataSource.stop()
    }
}