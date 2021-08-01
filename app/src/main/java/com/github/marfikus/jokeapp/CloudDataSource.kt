package com.github.marfikus.jokeapp

interface CloudDataSource {
    suspend fun getJoke(): Result<JokeServerModel, ErrorType>
}