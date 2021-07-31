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

    private var cachedJokeModel: JokeModel? = null

    private var getJokeFromCache = false

    override fun getJoke() {
        if (getJokeFromCache) {
            cacheDataSource.getJoke(object : JokeCachedCallback {
                override fun provide(jokeModel: JokeModel) {
                    cachedJokeModel = jokeModel
                    jokeCallback?.provide(jokeModel.toFavoriteJoke())
                }

                override fun fail() {
                    cachedJokeModel = null
                    jokeCallback?.provide(FailedJoke(noCachedJokes.getMessage()))
                }

            })
        } else {
            cloudDataSource.getJoke(object : JokeCloudCallback {
                override fun provide(jokeModel: JokeModel) {
                    cachedJokeModel = jokeModel

                    // check on favorite (if joke already in favorites)
                    jokeModel.checkExistInCache(cacheDataSource, object : CacheDataSourceCallback {
                        override fun onResult(exists: Boolean) {
                            if (exists) {
                                jokeCallback?.provide(jokeModel.toFavoriteJoke())
                            } else {
                                jokeCallback?.provide(jokeModel.toBaseJoke())
                            }
                        }
                    })
                }

                override fun fail(error: ErrorType) {
                    cachedJokeModel = null
                    val failure = if (error == ErrorType.NO_CONNECTION) noConnection else serviceUnavailable
                    jokeCallback?.provide(FailedJoke(failure.getMessage()))
                }
            })
        }
    }

    override fun changeJokeStatus(jokeCallback: JokeCallback) {
        cachedJokeModel?.changeStatus(cacheDataSource, object : ChangeStatusCallback {
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