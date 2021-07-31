package com.github.marfikus.jokeapp

interface JokeCloudCallback {

    fun provide(jokeModel: JokeModel)

    fun fail(error: ErrorType)
}

enum class ErrorType {
    NO_CONNECTION,
    SERVICE_UNAVAILABLE
}