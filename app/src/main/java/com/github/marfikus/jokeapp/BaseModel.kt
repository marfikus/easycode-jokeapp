package com.github.marfikus.jokeapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseModel(
    private val cacheDataSource: CacheDataSource,
    private val cloudDataSource: CloudDataSource,
    private val resourceManager: ResourceManager
) : Model {

    private val noConnection by lazy { NoConnection(resourceManager) }
    private val serviceUnavailable by lazy { ServiceUnavailable(resourceManager) }
    private val noCachedJokes by lazy { NoCachedJokes(resourceManager) }

    private var cachedJoke: Joke? = null
    private var getJokeFromCache = false

    override suspend fun getJoke(): JokeUiModel = withContext(Dispatchers.IO) {
        if (getJokeFromCache) {
            return@withContext when (val result = cacheDataSource.getJoke()) {
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
            return@withContext when (val result = cloudDataSource.getJoke()) {
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

    override suspend fun changeJokeStatus(): JokeUiModel? =
        withContext(Dispatchers.IO) {
            cachedJoke?.changeStatus(cacheDataSource)
        }

    override fun chooseDataSource(cached: Boolean) {
        getJokeFromCache = cached
    }


    private interface ResultHandler<S, E> {
        fun handleResult(result: Result<S, E>): JokeUiModel
    }

    private abstract inner class BaseResultHandler<S, E>
        (private val jokeDataFetcher: JokeDataFetcher<S, E>) : ResultHandler<S, E> {

            suspend fun process(): JokeUiModel {
                return handleResult(jokeDataFetcher.getJoke())
            }
        }

    private inner class CloudResultHandler(jokeDataFetcher: JokeDataFetcher<JokeServerModel, ErrorType>) :
            BaseResultHandler<JokeServerModel, ErrorType>(jokeDataFetcher) {

        override fun handleResult(result: Result<JokeServerModel, ErrorType>) = when (result) {
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

    private inner class CacheResultHandler(jokeDataFetcher: JokeDataFetcher<Joke, Unit>) :
            BaseResultHandler<Joke, Unit>(jokeDataFetcher) {

        override fun handleResult(result: Result<Joke, Unit>) = when (result) {
            is Result.Success<Joke> -> result.data.let {
                cachedJoke = it
                it.toFavoriteJoke()
            }
            is Result.Error -> {
                cachedJoke = null
                FailedJokeUiModel(noCachedJokes.getMessage())
            }
        }

    }
}