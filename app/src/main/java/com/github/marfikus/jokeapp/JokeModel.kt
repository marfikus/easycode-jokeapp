package com.github.marfikus.jokeapp

class JokeModel(
    private val id: Int,
    private val type: String,
    private val text: String,
    private val punchline: String,
) {

    fun toJokeRealm() = JokeRealm().also {
        it.id = id
        it.type = type
        it.text = text
        it.punchline = punchline
    }

    fun toBaseJoke() = BaseJoke(
        text = text,
        punchline = punchline
    )

    fun toFavoriteJoke() = FavoriteJoke(
        text = text,
        punchline = punchline
    )

    fun changeStatus(cacheDataSource: CacheDataSource, callback: ChangeStatusCallback) {
        cacheDataSource.addOrRemove(id, this, callback)
    }

    fun checkExistInCache(cacheDataSource: CacheDataSource, callback: CacheDataSourceCallback) {
        cacheDataSource.exists(id, callback)
    }
}