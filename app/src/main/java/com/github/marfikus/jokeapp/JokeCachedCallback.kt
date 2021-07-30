package com.github.marfikus.jokeapp

interface JokeCachedCallback {

    fun provide(jokeServerModel: JokeServerModel)

    fun fail()
}