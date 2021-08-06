package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.*
import com.github.marfikus.jokeapp.data.CacheDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseJokeRepository(
        private val cacheDataSource: CacheDataSource,
        private val cacheResultHandler: CacheResultHandler,
        private val cloudResultHandler: CloudResultHandler,
        private val cachedJoke: CachedJoke
) : JokeRepository {

    private var currentResultHandler: BaseResultHandler<*, *> = cloudResultHandler

    override fun chooseDataSource(cached: Boolean) {
        currentResultHandler = if (cached) cacheResultHandler else cloudResultHandler
    }

    override suspend fun getJoke(): JokeUiModel = withContext(Dispatchers.IO) {
        return@withContext currentResultHandler.process()
    }

    override suspend fun changeJokeStatus(): JokeUiModel? =
        withContext(Dispatchers.IO) {
            cachedJoke.changeStatus(cacheDataSource)
        }
}