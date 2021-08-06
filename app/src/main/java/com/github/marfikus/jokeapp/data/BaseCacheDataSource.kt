package com.github.marfikus.jokeapp.data

import android.util.Log
import com.github.marfikus.jokeapp.*

class BaseCacheDataSource(private val realmProvider: RealmProvider) : CacheDataSource {

    override suspend fun getJoke(): Result<Joke, Unit> {
        realmProvider.provide().use {
            Log.d("threadLogTag", "getJoke cache current thread ${Thread.currentThread().name}")
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
        realmProvider.provide().use {
            Log.d("threadLogTag", "addOrRemove current thread ${Thread.currentThread().name}")
            val jokeRealm =
                it.where(JokeRealmModel::class.java).equalTo("id", id).findFirst()
            return if (jokeRealm == null) {
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