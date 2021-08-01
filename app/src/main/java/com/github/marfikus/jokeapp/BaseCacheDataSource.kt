package com.github.marfikus.jokeapp

import io.realm.Realm

class BaseCacheDataSource : CacheDataSource {

    override fun getJoke(jokeCachedCallback: JokeCachedCallback) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync {
            val jokes = it.where(JokeRealm::class.java).findAll()
            if (jokes.isEmpty()) {
                jokeCachedCallback.fail()
            } else {
                jokes.random().let { joke ->
                    jokeCachedCallback.provide(joke.toJoke())
                }
            }
        }
        realm.close()
    }

    override fun addOrRemove(id: Int, joke: Joke, modelCallback: ModelCallback) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync {
            val jokeRealm = it.where(JokeRealm::class.java).equalTo("id", id).findFirst()
            if (jokeRealm == null) {
                modelCallback.provide(joke.toFavoriteJoke())
                val newJoke = joke.toJokeRealm()
                it.insert(newJoke)
            } else {
                modelCallback.provide(joke.toBaseJoke())
                jokeRealm.deleteFromRealm()
            }
        }
        realm.close()
    }

}