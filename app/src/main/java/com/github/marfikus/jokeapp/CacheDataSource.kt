package com.github.marfikus.jokeapp

interface CacheDataSource {

    fun addOrRemove(id: Int,
                    jokeModel: JokeModel,
                    changeStatusCallback: ChangeStatusCallback)

    fun getJoke(jokeCachedCallback: JokeCachedCallback)

    fun exists(id: Int, callback: CheckExistCallback)

    fun init()

    fun stop()
}

interface CheckExistCallback {

    fun onResult(exists: Boolean)
}