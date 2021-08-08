package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.core.Mapper
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class JokeRealmModel : RealmObject(), Mapper<JokeDataModel> {
    @PrimaryKey
    var id: Int = -1
    var text: String = ""
    var punchline: String = ""

    override fun to() = JokeDataModel(id, text, punchline, true)
}