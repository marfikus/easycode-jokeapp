package com.github.marfikus.jokeapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class JokeRealm : RealmObject() {
    @PrimaryKey
    var id: Int = -1
    var text: String = ""
    var punchline: String = ""
    var type: String = ""

    fun toJoke() = Joke(
        id = id,
        type = type,
        text = text,
        punchline = punchline
    )
}