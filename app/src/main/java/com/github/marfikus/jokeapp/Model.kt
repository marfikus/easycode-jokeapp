package com.github.marfikus.jokeapp

interface Model {

    fun getJoke()

    fun init(callback: ResultCallback)

    fun clear()
}

interface ResultCallback {

    fun provideJoke(joke: Joke)
}