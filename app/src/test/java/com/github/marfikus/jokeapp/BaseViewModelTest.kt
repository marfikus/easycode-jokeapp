package com.github.marfikus.jokeapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.*
import org.junit.Test

class BaseViewModelTest {

    @ExperimentalCoroutinesApi
    @Test
    fun test_get_joke_from_cloud_success(): Unit = runBlocking {
        val model = TestModel()
        val communication = TestCommunication()
        val viewModel = BaseViewModel(model, communication, TestCoroutineDispatcher())

        model.success = true
        viewModel.chooseFavorites(false)
        viewModel.getJoke()

        val actualText = communication.text
        val actualId = communication.id
        val expectedText = "cloudJokeText\ncloudJokePunchline"
        assertEquals(expectedText, actualText)
        assertNotEquals(0, actualId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_get_joke_from_cloud_fail(): Unit = runBlocking {
        val model = TestModel()
        val communication = TestCommunication()
        val viewModel = BaseViewModel(model, communication, TestCoroutineDispatcher())

        model.success = false
        viewModel.chooseFavorites(false)
        viewModel.getJoke()

        val actualText = communication.text
        val actualId = communication.id
        val expectedText = "no connection\n"
        val expectedId = 0
        assertEquals(expectedText, actualText)
        assertEquals(expectedId , actualId)
    }

    private inner class TestModel : Model {

        private val cacheJokeUiModel = BaseJokeUiModel("cachedJokeText", "cachedJokePunchline")
        private val cacheJokeFailure = FailedJokeUiModel("cacheFailed")
        private val cloudJokeUiModel = BaseJokeUiModel("cloudJokeText", "cloudJokePunchline")
        private val cloudJokeFailure = FailedJokeUiModel("no connection")
        var success: Boolean = false
        private var getFromCache = false
        private var cachedJoke: JokeUiModel? = null

        override suspend fun getJoke(): JokeUiModel {
            return if (success) {
                if (getFromCache) {
                    cacheJokeUiModel.also {
                        cachedJoke = it
                    }
                } else {
                    cloudJokeUiModel.also {
                        cachedJoke = it
                    }
                }
            } else {
                cachedJoke = null
                if (getFromCache) {
                    cacheJokeFailure
                } else {
                    cloudJokeFailure
                }
            }
        }

        override suspend fun changeJokeStatus(): JokeUiModel? {
            TODO("Not yet implemented")
        }

        override fun chooseDataSource(cached: Boolean) {
            getFromCache = cached
        }
    }

    private inner class TestCommunication: Communication {

        var text = ""
        var id = -1
        var observeCount = 0

        override fun showData(data: Pair<String, Int>) {
            text = data.first
            id = data.second
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<Pair<String, Int>>) {
            observeCount++
        }
    }
}