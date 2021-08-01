package com.github.marfikus.jokeapp

import retrofit2.http.GET

interface JokeService {

    @GET("https://official-joke-api.appspot.com/random_joke/")
    suspend fun getJoke() : JokeServerModel
}