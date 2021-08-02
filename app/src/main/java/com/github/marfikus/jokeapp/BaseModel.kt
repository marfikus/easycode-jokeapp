package com.github.marfikus.jokeapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseModel(
    private val cacheDataSource: CacheDataSource,
    cloudDataSource: CloudDataSource,
    private val resourceManager: ResourceManager
) : Model {

    private var cachedJoke: Joke? = null
    private val cacheResultHandler by lazy { CacheResultHandler(cacheDataSource) }
    private val cloudResultHandler = CloudResultHandler(cloudDataSource)

    private var currentResultHandler: BaseResultHandler<*, *> = cloudResultHandler

    override fun chooseDataSource(cached: Boolean) {
        currentResultHandler = if (cached) cacheResultHandler else cloudResultHandler
    }

    override suspend fun getJoke(): JokeUiModel = withContext(Dispatchers.IO) {
        return@withContext currentResultHandler.process()
    }

    override suspend fun changeJokeStatus(): JokeUiModel? =
        withContext(Dispatchers.IO) {
            cachedJoke?.changeStatus(cacheDataSource)
        }


    private abstract inner class BaseResultHandler<S, E>
        (private val jokeDataFetcher: JokeDataFetcher<S, E>) {

            suspend fun process(): JokeUiModel {
                return handleResult(jokeDataFetcher.getJoke())
            }

            protected abstract fun handleResult(result: Result<S, E>): JokeUiModel
        }

    private inner class CloudResultHandler(jokeDataFetcher: JokeDataFetcher<JokeServerModel, ErrorType>) :
            BaseResultHandler<JokeServerModel, ErrorType>(jokeDataFetcher) {

        private val noConnection by lazy { NoConnection(resourceManager) }
        private val serviceUnavailable by lazy { ServiceUnavailable(resourceManager) }

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

        private val noCachedJokes by lazy { NoCachedJokes(resourceManager) }

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