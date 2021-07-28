package com.github.marfikus.jokeapp

interface Error {

    fun getMessage(): String
}

class NoConnection : Error {

    override fun getMessage(): String {
        TODO("Not yet implemented")
    }
}

class ServiceUnavailable : Error {

    override fun getMessage(): String {
        TODO("Not yet implemented")
    }
}