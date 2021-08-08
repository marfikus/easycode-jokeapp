package com.github.marfikus.jokeapp

/*
import com.github.marfikus.jokeapp.data.Result
import com.github.marfikus.jokeapp.domain.Joke

class CacheResultHandler(
        private val cachedJoke: CachedJoke,
        jokeDataFetcher: JokeDataFetcher<Joke, Unit>,
        private val noCachedJokes: JokeFailure
) : BaseResultHandler<Joke, Unit>(jokeDataFetcher) {

    override fun handleResult(result: Result<Joke, Unit>) = when (result) {
        is Result.Success<Joke> -> result.data.let {
            cachedJoke.saveJoke(it)
            it.toFavoriteJoke()
        }
        is Result.Error -> {
            cachedJoke.clear()
            FailedJokeUiModel(noCachedJokes.getMessage())
        }
    }

}*/
