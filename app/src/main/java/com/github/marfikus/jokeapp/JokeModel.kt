package com.github.marfikus.jokeapp

class JokeModel(
    private val id: Int,
    private val type: String,
    private val text: String,
    private val punchline: String,
) {

    fun toJokeServerModel() = JokeServerModel(
        id = id,
        type = type,
        text = text,
        punchline = punchline
    )

    fun toJokeRealm() = JokeRealm().also {
        it.id = id
        it.type = type
        it.text = text
        it.punchLine = punchline
    }

    fun toBaseJoke() = BaseJoke(
        text = text,
        punchline = punchline
    )

    fun toFavoriteJoke() = FavoriteJoke(
        text = text,
        punchline = punchline
    )

    fun changeStatus(cacheDataSource: CacheDataSource, modelCallback: ModelCallback) {
        cacheDataSource.addOrRemove(id, this, modelCallback)
    }

    fun checkExistInCache(cacheDataSource: CacheDataSource, callback: CacheDataSourceCallback) {
        cacheDataSource.exists(id, callback)
    }
}