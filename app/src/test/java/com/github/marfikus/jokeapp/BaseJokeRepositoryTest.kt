package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.BaseCachedJoke
import com.github.marfikus.jokeapp.data.BaseJokeRepository
import com.github.marfikus.jokeapp.data.TestCacheDataSource
import com.github.marfikus.jokeapp.data.TestCloudDataSource
import com.github.marfikus.jokeapp.presentation.BaseJokeUiModel
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

class BaseJokeRepositoryTest {

    @Test
    fun test_change_data_source(): Unit = runBlocking {
        val cacheDataSource = TestCacheDataSource()
        val cloudDataSource = TestCloudDataSource()
        val cachedJoke = BaseCachedJoke()
        val resourceManager = TestResourceManager()
        val model = BaseJokeRepository(
            cacheDataSource,
            CacheResultHandler(
                cachedJoke,
                cacheDataSource,
                NoCachedJokes(resourceManager)
            ),
            CloudResultHandler(
                cachedJoke,
                cloudDataSource,
                NoConnection(resourceManager),
                ServiceUnavailable(resourceManager)
            ),
            cachedJoke
        )
        model.chooseDataSource(false)
        cloudDataSource.getJokeWithResult(true)
        val joke = model.getJoke()
        assertEquals(joke is BaseJokeUiModel, true)
        model.changeJokeStatus()
        assertEquals(cacheDataSource.checkContainsId(0), true)
    }

}