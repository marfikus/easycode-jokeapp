package com.github.marfikus.jokeapp

class TestCloudDataSource : CloudDataSource {

    override fun getJoke(callback: JokeCloudCallback) {
        callback.provide(JokeServerModel(0, "testType", "testText", "testPunchline"))
    }
}