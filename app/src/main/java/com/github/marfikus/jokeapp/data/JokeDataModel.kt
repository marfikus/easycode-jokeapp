package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.ChangeJoke
import com.github.marfikus.jokeapp.ChangeJokeStatus

class JokeDataModel(
        private val id: Int,
        val text: String,
        val punchline: String,
        val cahched: Boolean = false
) : ChangeJoke {

    override suspend fun change(changeJokeStatus: ChangeJokeStatus) =
            changeJokeStatus.addOrRemove(id, this)

    fun toRealm() = JokeRealmModel().also { joke ->
        joke.id = id
        joke.text = text
        joke.punchline = punchline
    }

    fun changeCached(cached: Boolean): JokeDataModel {
        return JokeDataModel(id, text, punchline, cached)
    }
}