package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class BaseJokeRepository(
        private val cacheDataSource: CacheDataSource,
        private val cloudDataSource: CloudDataSource,
        private val cachedJoke: CachedJoke
) : JokeRepository {

    private var currentDataSource: JokeDataFetcher = cloudDataSource

    override fun chooseDataSource(cached: Boolean) {
        currentDataSource = if (cached) cacheDataSource else cloudDataSource
    }

    override suspend fun getJoke(): JokeDataModel = withContext(Dispatchers.IO) {
        try {
            val joke = currentDataSource.getJoke()
            cachedJoke.saveJoke(joke)
            return@withContext joke
        } catch (e: Exception) {
            cachedJoke.clear()
            throw e
        }
    }

    override suspend fun changeJokeStatus(): JokeDataModel = cachedJoke.change(cacheDataSource)
}