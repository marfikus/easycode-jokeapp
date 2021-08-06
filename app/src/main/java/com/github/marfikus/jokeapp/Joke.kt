package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.data.JokeRealmModel

class Joke(
    private val id: Int,
    private val type: String,
    private val text: String,
    private val punchline: String
) : ChangeJoke {

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

    override suspend fun changeStatus(changeJokeStatus: ChangeJokeStatus) =
        changeJokeStatus.addOrRemove(id, this)
}