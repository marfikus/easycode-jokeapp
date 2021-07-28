package com.github.marfikus.jokeapp

interface Joke {

    fun getJokeUi(): String
}

class TestJoke(private val text: String, private val punchline: String) : Joke {

    // TODO: 28.07.21 сделать мапер
    override fun getJokeUi() = "$text\n$punchline"
}