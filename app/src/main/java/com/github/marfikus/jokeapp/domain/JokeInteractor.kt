package com.github.marfikus.jokeapp.domain

interface JokeInteractor {

    suspend fun getJoke(): Joke

    suspend fun changeFavorites(): Joke

    fun getFavoriteJokes(favorites: Boolean)
}