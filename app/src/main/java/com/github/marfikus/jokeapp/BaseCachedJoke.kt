package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.JokeDataModel

class BaseCachedJoke : CachedJoke {

    private var cached: JokeDataModel? = null

    override fun saveJoke(joke: JokeDataModel) {
        cached = joke
    }

    override fun clear() {
        cached = null
    }

    override suspend fun change(changeJokeStatus: ChangeJokeStatus): JokeDataModel? {
        return cached?.change(changeJokeStatus)
    }
}