package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.JokeServerModel
import com.github.marfikus.jokeapp.data.Result

class CloudResultHandler(
        private val cachedJoke: CachedJoke,
        jokeDataFetcher: JokeDataFetcher<JokeServerModel, ErrorType>,
        private val noConnection: JokeFailure,
        private val serviceUnavailable: JokeFailure,
) : BaseResultHandler<JokeServerModel, ErrorType>(jokeDataFetcher) {

    override fun handleResult(result: Result<JokeServerModel, ErrorType>) = when (result) {
        is Result.Success<JokeServerModel> -> {
            result.data.toJoke().let {
                cachedJoke.saveJoke(it)
                it.toBaseJoke()
            }
        }
        is Result.Error<ErrorType> -> {
            cachedJoke.clear()
            val failure = if (result.exception == ErrorType.NO_CONNECTION)
                noConnection
            else
                serviceUnavailable
            FailedJokeUiModel(failure.getMessage())
        }
    }

}