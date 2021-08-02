package com.github.marfikus.jokeapp

interface ChangeJoke {
    suspend fun changeStatus(changeJokeStatus: ChangeJokeStatus): JokeUiModel?
}