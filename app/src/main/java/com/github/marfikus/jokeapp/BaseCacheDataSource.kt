package com.github.marfikus.jokeapp

import io.realm.Realm

class BaseCacheDataSource : CacheDataSource {

    private lateinit var realm: Realm

    override fun getJoke(jokeCachedCallback: JokeCachedCallback) {
        realm.executeTransactionAsync {
            val jokes = it.where(JokeRealm::class.java).findAll()
            if (jokes.isEmpty()) {
                jokeCachedCallback.fail()
            } else {
                jokes.random().let { joke ->
                    jokeCachedCallback.provide(
                            JokeServerModel(joke.id, joke.type, joke.text, joke.punchLine)
                    )
                }
            }
        }
    }

    override fun addOrRemove(id: Int,
                             jokeServerModel: JokeServerModel,
                             modelCallback: ModelCallback) {

        realm.executeTransactionAsync {
            val jokeRealm = it.where(JokeRealm::class.java).equalTo("id", id).findFirst()
            if (jokeRealm == null) {
                modelCallback.provide(jokeServerModel.toFavoriteJoke())
                val newJoke = jokeServerModel.toJokeRealm()
                it.insert(newJoke)
            } else {
                modelCallback.provide(jokeServerModel.toBaseJoke())
                jokeRealm.deleteFromRealm()
            }
        }
    }

    override fun exists(id: Int, callback: CacheDataSourceCallback) {
        realm.executeTransactionAsync {
            val jokeRealm = it.where(JokeRealm::class.java).equalTo("id", id).findFirst()
            callback.onResult(jokeRealm != null)
        }
    }

    override fun init() {
        realm = Realm.getDefaultInstance()
    }

    override fun stop() {
        realm.close()
    }

}