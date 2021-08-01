package com.github.marfikus.jokeapp

interface CacheDataSource {
    fun getJoke(jokeCachedCallback: JokeCachedCallback)
    fun addOrRemove(id: Int, joke: Joke, changeStatusCallback: ChangeStatusCallback)
}

interface JokeCachedCallback {
    fun provide(joke: Joke)
    fun fail()
}