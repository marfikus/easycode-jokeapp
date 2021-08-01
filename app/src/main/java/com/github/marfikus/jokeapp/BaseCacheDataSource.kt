package com.github.marfikus.jokeapp

import io.realm.Realm

class BaseCacheDataSource : CacheDataSource {

    override suspend fun getJoke(): Result<Joke, Unit> {
        val realm = Realm.getDefaultInstance()

        realm.use {
            val jokes = it.where(JokeRealmModel::class.java).findAll()
            if (jokes.isEmpty()) {
                return Result.Error(Unit)
            } else {
                jokes.random().let { joke ->
                    return Result.Success(joke.toJoke())
                }
            }
        }
//        realm.close()
    }

    override fun addOrRemove(id: Int, joke: Joke, changeStatusCallback: ChangeStatusCallback) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync {
            val jokeRealm = it.where(JokeRealmModel::class.java).equalTo("id", id).findFirst()
            if (jokeRealm == null) {
                changeStatusCallback.provide(joke.toFavoriteJoke())
                val newJoke = joke.toJokeRealm()
                it.insert(newJoke)
            } else {
                changeStatusCallback.provide(joke.toBaseJoke())
                jokeRealm.deleteFromRealm()
            }
        }
        realm.close()
    }

}