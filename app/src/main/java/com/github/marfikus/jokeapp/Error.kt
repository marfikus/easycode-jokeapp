package com.github.marfikus.jokeapp

interface Error {

    fun getMessage(): String
}

class NoConnection(private val resourceManager: ResourceManager) : Error {

    override fun getMessage() = resourceManager.getString(R.string.no_connection)
}

class ServiceUnavailable(private val resourceManager: ResourceManager) : Error {

    override fun getMessage() = resourceManager.getString(R.string.service_unavailable)
}