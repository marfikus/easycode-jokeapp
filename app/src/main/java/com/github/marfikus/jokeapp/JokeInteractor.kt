package com.github.marfikus.jokeapp

interface JokeInteractor {

    suspend fun getJoke(): Joke

    suspend fun changeFavorites(): Joke

    suspend fun getFavoriteJokes(favorites: Boolean)
}