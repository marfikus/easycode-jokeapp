package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.Joke
import com.google.gson.annotations.SerializedName

data class JokeServerModel(
    @SerializedName("id")
    private val id: Int,
    @SerializedName("type")
    private val type: String,
    @SerializedName("setup")
    private val text: String,
    @SerializedName("punchline")
    private val punchline: String
) {

    fun toJokeDataModel() = JokeDataModel(id, type, text, punchline)

    fun toJoke() = Joke(
        id = id,
        type = type,
        text = text,
        punchline = punchline
    )
}
