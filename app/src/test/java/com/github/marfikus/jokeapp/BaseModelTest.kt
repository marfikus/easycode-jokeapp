package com.github.marfikus.jokeapp

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

class BaseModelTest {

    @Test
    fun test_change_data_source(): Unit = runBlocking {
        val cacheDataSource = TestCacheDataSource()
        val cloudDataSource = TestCloudDataSource()
        val model = BaseModel(cacheDataSource, cloudDataSource, TestResourceManager())
        model.chooseDataSource(false)
        cloudDataSource.getJokeWithResult(true)
        val joke = model.getJoke()
        assertEquals(joke is BaseJokeUiModel, true)
        model.changeJokeStatus()
        assertEquals(cacheDataSource.checkContainsId(0), true)
    }

}