package com.github.marfikus.jokeapp

class Joke(
    private val id: Int,
    private val type: String,
    private val text: String,
    private val punchline: String
) {

    fun toBaseJoke() = BaseJokeUiModel(text, punchline)

    fun toFavoriteJoke() = FavoriteJokeUiModel(text, punchline)

    fun toJokeRealm(): JokeRealmModel {
        return JokeRealmModel().also {
            it.id = id
            it.type = type
            it.text = text
            it.punchline = punchline
        }
    }

    fun changeStatus(cacheDataSource: CacheDataSource, changeStatusCallback: ChangeStatusCallback) {
        cacheDataSource.addOrRemove(id, this, changeStatusCallback)
    }
}