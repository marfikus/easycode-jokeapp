package com.github.marfikus.jokeapp

interface CacheDataSource {

    fun addOrRemove(id: Int,
                    jokeServerModel: JokeServerModel,
                    modelCallback: ModelCallback)

    fun getJoke(jokeCachedCallback: JokeCachedCallback)
}