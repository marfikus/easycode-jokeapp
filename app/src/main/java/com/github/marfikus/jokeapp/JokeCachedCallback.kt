package com.github.marfikus.jokeapp

interface JokeCachedCallback {

    fun provide(jokeModel: JokeModel)

    fun fail()
}