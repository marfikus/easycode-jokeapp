package com.github.marfikus.jokeapp

interface ChangeStatusCallback {

    fun provide(joke: Joke)
}