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
                jokes.random().let { jokeRealm ->
                    jokeCachedCallback.provide(jokeRealm.toJokeModel())
                }
            }
        }
    }

    override fun addOrRemove(id: Int,
                             jokeModel: JokeModel,
                             changeStatusCallback: ChangeStatusCallback) {

        realm.executeTransactionAsync {
            val jokeRealm = it.where(JokeRealm::class.java).equalTo("id", id).findFirst()
            if (jokeRealm == null) {
                changeStatusCallback.provide(jokeModel.toFavoriteJoke())
                val newJoke = jokeModel.toJokeRealm()
                it.insert(newJoke)
            } else {
                changeStatusCallback.provide(jokeModel.toBaseJoke())
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