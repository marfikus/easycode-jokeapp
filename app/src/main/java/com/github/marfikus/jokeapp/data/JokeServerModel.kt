package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.core.Mapper
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
) : Mapper<JokeDataModel> {

    override fun to() = JokeDataModel(id, text, punchline)
}
