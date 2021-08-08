package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.domain.Joke
import java.lang.Exception

class BaseJokeInteractor(
        private val repository: JokeRepository,
        private val jokeFailureHandler: JokeFailureHandler
) : JokeInteractor {

    override suspend fun getJoke(): Joke {
        return try {
            repository.getJoke().toJoke()
        } catch (e: Exception) {
            Joke.Failed(jokeFailureHandler.handle(e))
        }
    }

    override suspend fun changeFavorites(): Joke {
        return try {
            repository.changeJokeStatus().toJoke()
        } catch (e: Exception) {
            Joke.Failed(jokeFailureHandler.handle(e))
        }
    }

    override fun getFavoriteJokes(favorites: Boolean) {
        repository.chooseDataSource(favorites)
    }
}