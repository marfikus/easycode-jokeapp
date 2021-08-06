package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.domain.Joke
import com.github.marfikus.jokeapp.domain.NoCachedJokesException
import com.github.marfikus.jokeapp.domain.NoConnectionException
import com.github.marfikus.jokeapp.domain.ServiceUnavailableException
import java.lang.Exception

class BaseJokeInteractor(
        private val repository: JokeRepository,
        private val resourceManager: ResourceManager
) : JokeInteractor {

    override suspend fun getJoke(): Joke {
        return try {
            Joke.Success(repository.getJoke().text, repository.getJoke().punchline, false)
        } catch (e: Exception) {
            val message = when (e) {
                is NoConnectionException -> NoConnection(resourceManager).getMessage()
                is NoCachedJokesException -> NoCachedJokes(resourceManager).getMessage()
                is ServiceUnavailableException -> ServiceUnavailable(resourceManager).getMessage()
                else -> resourceManager.getString(R.string.generic_fail_message)
            }
            Joke.Failed(message)
        }
    }

    override suspend fun changeFavorites(): Joke {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteJokes(favorites: Boolean) {
        TODO("Not yet implemented")
    }
}