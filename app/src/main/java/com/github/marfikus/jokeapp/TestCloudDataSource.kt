package com.github.marfikus.jokeapp

class TestCloudDataSource : CloudDataSource {

    private var success = true
    private var count = 0

    override suspend fun getJoke(): Result<JokeServerModel, ErrorType> {
        return if (success) {
            Result.Success(JokeServerModel(count++, "testType", "testText$count", "testPunchline$count"))
        } else {
            Result.Error(ErrorType.NO_CONNECTION)
        }
    }
}
