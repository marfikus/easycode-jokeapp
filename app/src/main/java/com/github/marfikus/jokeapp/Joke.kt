package com.github.marfikus.jokeapp

class Joke(private val text: String, private val punchline: String) {

    // TODO: 28.07.21 сделать мапер и добавить интерфейс (как в ошибке)
    fun getJokeUi() = "$text\n$punchline"
}