package com.github.marfikus.jokeapp

class TestCacheDataSource : CacheDataSource {

    private val list = ArrayList<Pair<Int, Joke>>()

    override fun getJoke(jokeCachedCallback: JokeCachedCallback) {
        if (list.isEmpty()) {
            jokeCachedCallback.fail()
        } else {
            jokeCachedCallback.provide(list.random().second)
        }
    }

    override fun addOrRemove(id: Int, joke: Joke, changeStatusCallback: ChangeStatusCallback) {
        val found = list.find { it.first == id }

        if (found == null) {
            changeStatusCallback.provide(joke.toFavoriteJoke())
            list.add(Pair(id, joke))
        } else {
            changeStatusCallback.provide(joke.toBaseJoke())
            list.remove(found)
        }
    }
}
