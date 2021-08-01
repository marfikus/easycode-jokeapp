package com.github.marfikus.jokeapp

interface CacheDataSource {
    suspend fun getJoke(): Result<Joke, Unit>
    fun addOrRemove(id: Int, joke: Joke, changeStatusCallback: ChangeStatusCallback)
}

interface JokeCachedCallback {
    fun provide(joke: Joke)
    fun fail()
}