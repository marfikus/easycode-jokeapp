package com.github.marfikus.jokeapp.data

class JokeDataModel(
        private val id: Int,
        private val type: String,
        val text: String,
        val punchline: String,
) {
}