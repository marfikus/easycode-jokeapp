package com.github.marfikus.jokeapp

class TestCacheDataSource : CacheDataSource {

    private val map = HashMap<Int, JokeServerModel>()

    override fun addOrRemove(id: Int, jokeServerModel: JokeServerModel): Joke {
        return if (map.containsKey(id)) {
            val joke = map[id]!!.toBaseJoke()
            map.remove(id)
            joke
        } else {
            map[id] = jokeServerModel
            jokeServerModel.toFavoriteJoke()
        }
    }

    override fun getJoke(jokeCachedCallback: JokeCachedCallback) {
        if (map.isEmpty())
            jokeCachedCallback.fail()
        else
            jokeCachedCallback.provide(map[0]!!)
    }
}