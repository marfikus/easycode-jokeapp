package com.github.marfikus.jokeapp

interface CacheDataSource {

    fun addOrRemove(id: Int, jokeServerModel: JokeServerModel): Joke

    fun getJoke(jokeCachedCallback: JokeCachedCallback)
}