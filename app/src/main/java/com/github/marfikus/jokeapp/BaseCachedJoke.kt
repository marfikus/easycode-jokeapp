package com.github.marfikus.jokeapp

class BaseCachedJoke : CachedJoke {

    private var cached: Joke? = null

    override fun saveJoke(joke: Joke) {
        cached = joke
    }

    override fun clear() {
        cached = null
    }

    override suspend fun changeStatus(changeJokeStatus: ChangeJokeStatus): JokeUiModel? {
        return cached?.changeStatus(changeJokeStatus)
    }
}