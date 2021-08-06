package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.domain.Joke
import com.github.marfikus.jokeapp.JokeUiModel

class TestCacheDataSource : CacheDataSource {

    private val map = HashMap<Int, Joke>()
    private var success = true
    private var nextJokeIdToGet = -1

    fun getNextJokeWithResult(success: Boolean, id: Int) {
        this.success = success
        nextJokeIdToGet = id
    }

    override suspend fun getJoke(): Result<Joke, Unit> {
        return if (success) {
            Result.Success(map[nextJokeIdToGet]!!)
        } else {
            Result.Error(Unit)
        }
    }

    override suspend fun addOrRemove(id: Int, joke: Joke): JokeUiModel {
        return if (map.containsKey(id)) {
            val result = map[id]!!.toBaseJoke()
            map.remove(id)
            result
        } else {
            map[id] = joke
            joke.toFavoriteJoke()
        }
    }

    fun checkContainsId(id: Int) = map.containsKey(id)
}
