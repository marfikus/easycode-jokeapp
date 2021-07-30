package com.github.marfikus.jokeapp

import io.realm.Realm

class BaseCacheDataSource(private val realm: Realm) : CacheDataSource {

    override fun getJoke(jokeCachedCallback: JokeCachedCallback) {
        realm.use {
            val jokes = it.where(JokeRealm::class.java).findAll()
            if (jokes.isEmpty()) {
                jokeCachedCallback.fail()
            } else {
                jokes.random().let { joke ->
                    jokeCachedCallback.provide(
                        JokeServerModel(
                            joke.id,
                            joke.type,
                            joke.text,
                            joke.punchLine
                        )
                    )
                }
            }
        }
    }

    override fun addOrRemove(id: Int, jokeServerModel: JokeServerModel): Joke {
        realm.use {
            val jokeRealm = it.where(JokeRealm::class.java).equalTo("id", id).findFirst()
            return if (jokeRealm == null) {
                val newJoke = jokeServerModel.toJokeRealm()
                it.executeTransaction { transaction ->
                    transaction.insert(newJoke)
                }
                jokeServerModel.toFavoriteJoke()
            } else {
                it.executeTransaction {
                    jokeRealm.deleteFromRealm()
                }
                jokeServerModel.toBaseJoke()
            }
        }
    }
}