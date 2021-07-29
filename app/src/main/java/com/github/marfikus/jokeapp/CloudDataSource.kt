package com.github.marfikus.jokeapp

interface CloudDataSource {

    fun getJoke(callback: JokeCloudCallback)
}