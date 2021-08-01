package com.github.marfikus.jokeapp

abstract class JokeUiModel(private val text: String, private val punchline: String) {

    private fun text() = "$text\n$punchline"
}

class BaseJokeUiModel(text: String, punchline: String) : JokeUiModel(text, punchline) {

}

class FavoriteJokeUiModel(text: String, punchline: String) : JokeUiModel(text, punchline) {

}

class FailedJokeUiModel(text: String) : JokeUiModel(text, "") {

}