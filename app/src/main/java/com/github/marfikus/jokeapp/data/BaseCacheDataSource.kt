package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.*
import com.github.marfikus.jokeapp.domain.NoCachedJokesException

class BaseCacheDataSource(private val realmProvider: RealmProvider) : CacheDataSource {

    override suspend fun getJoke(): JokeDataModel {
        realmProvider.provide().use {
            val jokes = it.where(JokeRealmModel::class.java).findAll()
            if (jokes.isEmpty()) {
                throw NoCachedJokesException()
            } else {
                return jokes.random().to()
            }
        }
    }

    override suspend fun addOrRemove(id: Int, joke: Joke): JokeUiModel =
        realmProvider.provide().use {
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