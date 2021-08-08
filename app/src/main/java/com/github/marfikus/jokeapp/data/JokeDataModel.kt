package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.ChangeJoke
import com.github.marfikus.jokeapp.ChangeJokeStatus
import com.github.marfikus.jokeapp.domain.Joke

class JokeDataModel(
        private val id: Int,
        private val text: String,
        private val punchline: String,
        private val cached: Boolean = false
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

    fun toJoke() = Joke.Success(text, punchline, cached)
}