package com.github.marfikus.jokeapp

import com.github.marfikus.jokeapp.domain.Joke

interface JokeInteractor {

    suspend fun getJoke(): Joke

    suspend fun changeFavorites(): Joke

    fun getFavoriteJokes(favorites: Boolean)
}