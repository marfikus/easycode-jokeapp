package com.github.marfikus.jokeapp

interface CacheDataSource {

    fun addOrRemove(id: Int, joke: JokeServerModel): Joke

    fun getJoke(jokeCachedCallback: JokeCachedCallback)
}