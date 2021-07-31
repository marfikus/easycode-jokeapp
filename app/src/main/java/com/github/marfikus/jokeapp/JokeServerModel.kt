package com.github.marfikus.jokeapp

import com.google.gson.annotations.SerializedName

data class JokeServerModel(
    @SerializedName("id")
    private val id: Int,
    @SerializedName("type")
    private val type: String,
    @SerializedName("setup")
    private val text: String,
    @SerializedName("punchline")
    private val punchline: String
) {

    fun toBaseJoke() = BaseJoke(text, punchline)

    fun toFavoriteJoke() = FavoriteJoke(text, punchline)

    fun toJokeRealm(): JokeRealm {
        return JokeRealm().also {
            it.id = id
            it.type = type
            it.text = text
            it.punchLine = punchline
        }
    }

    fun changeStatus(cacheDataSource: CacheDataSource, changeStatusCallback: ChangeStatusCallback) {
        cacheDataSource.addOrRemove(id, this, changeStatusCallback)
    }

    fun checkExistInCache(cacheDataSource: CacheDataSource, callback: CacheDataSourceCallback) {
        cacheDataSource.exists(id, callback)
    }
}
