package com.github.marfikus.jokeapp.data

class JokeDataModel(
        private val id: Int,
        private val text: String,
        private val punchline: String,
        private val cached: Boolean = false
) : ChangeJoke {

    fun <T> map(mapper: JokeDataModelMapper<T>): T {
        return mapper.map(id, text, punchline, cached)
    }

    override suspend fun change(changeJokeStatus: ChangeJokeStatus) =
            changeJokeStatus.addOrRemove(id, this)

    fun changeCached(cached: Boolean): JokeDataModel {
        return JokeDataModel(id, text, punchline, cached)
    }
}