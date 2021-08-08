package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.domain.Joke
import com.github.marfikus.jokeapp.domain.NoCachedJokesException
import com.github.marfikus.jokeapp.domain.NoConnectionException
import com.github.marfikus.jokeapp.domain.ServiceUnavailableException
import java.lang.Exception

class BaseJokeInteractor(
        private val repository: JokeRepository,
        private val jokeFailureHandler: JokeFailureHandler
) : JokeInteractor {

    override suspend fun getJoke(): Joke {
        return try {
            Joke.Success(repository.getJoke().text, repository.getJoke().punchline, false)
        } catch (e: Exception) {
            Joke.Failed(jokeFailureHandler.handle(e))
        }
    }

    override suspend fun changeFavorites(): Joke {
        return try {
            val joke = repository.changeJokeStatus()
            Joke.Success(joke.text, joke.punchline)
        } catch (e: Exception) {
            Joke.Failed(jokeFailureHandler.handle(e))
        }
    }

    override suspend fun getFavoriteJokes(favorites: Boolean) {
        repository.chooseDataSource(favorites)
    }
}