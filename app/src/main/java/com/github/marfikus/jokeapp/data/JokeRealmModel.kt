package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.Joke
import com.github.marfikus.jokeapp.core.Mapper
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class JokeRealmModel : RealmObject(), Mapper<JokeDataModel> {
    @PrimaryKey
    var id: Int = -1
    var text: String = ""
    var punchline: String = ""
    var type: String = ""

    override fun to() = JokeDataModel(id, type, text, punchline)

    fun toJoke() = Joke(
        id = id,
        type = type,
        text = text,
        punchline = punchline
    )
}