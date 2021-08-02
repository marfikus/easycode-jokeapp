package com.github.marfikus.jokeapp

interface CacheDataSource : ChangeJokeStatus {
    suspend fun getJoke(): Result<Joke, Unit>
}