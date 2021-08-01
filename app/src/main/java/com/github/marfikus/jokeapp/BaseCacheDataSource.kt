package com.github.marfikus.jokeapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseCacheDataSource(private val realmProvider: RealmProvider) : CacheDataSource {

    override suspend fun getJoke(): Result<Joke, Unit> {
        realmProvider.provide().use {
            val jokes = it.where(JokeRealmModel::class.java).findAll()
            if (jokes.isEmpty()) {
                return Result.Error(Unit)
            } else {
                jokes.random().let { joke ->
                    return Result.Success(joke.toJoke())
                }
            }
        }
    }

    override suspend fun addOrRemove(id: Int, joke: Joke): JokeUiModel =
        withContext(Dispatchers.IO) {
            realmProvider.provide().use {
                val jokeRealm =
                    it.where(JokeRealmModel::class.java).equalTo("id", id).findFirst()
                return@withContext if (jokeRealm == null) {
                    it.executeTransaction { transaction ->
                        val newJoke = joke.toJokeRealm()
                        transaction.insert(newJoke)
                    }
                    joke.toFavoriteJoke()
                } else {
                    it.executeTransaction {
                        jokeRealm.deleteFromRealm()
                    }
                    joke.toBaseJoke()
                }
            }
        }
}