package com.github.marfikus.jokeapp

class TestCloudDataSource : CloudDataSource {

    private var count = 0

    override fun getJoke(callback: JokeCloudCallback) {
        val joke = JokeServerModel(count, "testType", "testText$count", "testPunchline$count")
        callback.provide(joke.toJokeModel())
        count++
        if (count == 3)
            count = 0
    }
}