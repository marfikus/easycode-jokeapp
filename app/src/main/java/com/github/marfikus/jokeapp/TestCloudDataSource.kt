package com.github.marfikus.jokeapp

class TestCloudDataSource : CloudDataSource {

    private var success = true
    private var count = 0

    fun getJokeWithResult(success: Boolean) {
        this.success = success
    }

    override suspend fun getJoke(): Result<JokeServerModel, ErrorType> {
        return if (success) {
            Result.Success(JokeServerModel(count++, "testType", "testText$count", "testPunchline$count"))
        } else {
            Result.Error(ErrorType.NO_CONNECTION)
        }
    }
}
