package com.github.marfikus.jokeapp

interface CacheDataSource {
    fun getJoke(jokeCachedCallback: JokeCachedCallback)
    fun addOrRemove(id: Int, joke: Joke, modelCallback: ModelCallback)
}

interface JokeCachedCallback {
    fun provide(jokeServerModel: JokeServerModel)
    fun fail()
}