package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.domain.NoCachedJokesException
import com.github.marfikus.jokeapp.domain.NoConnectionException
import com.github.marfikus.jokeapp.domain.ServiceUnavailableException
import java.lang.Exception

interface JokeFailureHandler {
    fun handle(e: Exception): JokeFailure
}

class JokeFailureFactory(private val resourceManager: ResourceManager) : JokeFailureHandler {

    override fun handle(e: Exception): JokeFailure {
        return when (e) {
            is NoConnectionException -> NoConnection(resourceManager)
            is NoCachedJokesException -> NoCachedJokes(resourceManager)
            is ServiceUnavailableException -> ServiceUnavailable(resourceManager)
            else -> GenericError(resourceManager)
        }
    }
}