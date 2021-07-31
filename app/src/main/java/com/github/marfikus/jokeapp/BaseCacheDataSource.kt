package com.github.marfikus.jokeapp

import io.realm.Realm
import io.realm.RealmConfiguration

class BaseCacheDataSource(private val realmConfig: RealmConfiguration) : CacheDataSource {

    override fun getJoke(jokeCachedCallback: JokeCachedCallback) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAsync {
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
//        realm.close()
    }

    override fun addOrRemove(id: Int,
                             jokeServerModel: JokeServerModel,
                             modelCallback: ModelCallback) {

        val realm = Realm.getInstance(realmConfig)
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
//        realm.close()
        }

}