package com.github.marfikus.jokeapp

interface CacheDataSource {

    fun addOrRemove(id: Int,
                    jokeServerModel: JokeServerModel,
                    modelCallback: ModelCallback)

    fun getJoke(jokeCachedCallback: JokeCachedCallback)

    fun exists(id: Int, callback: CacheDataSourceCallback)

    fun init()

    fun stop()
}

interface CacheDataSourceCallback {

    fun onResult(exists: Boolean)
}