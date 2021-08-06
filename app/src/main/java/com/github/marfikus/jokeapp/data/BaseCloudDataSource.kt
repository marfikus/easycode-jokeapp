package com.github.marfikus.jokeapp.data

import android.util.Log
import com.github.marfikus.jokeapp.ErrorType
import java.lang.Exception
import java.net.UnknownHostException

class BaseCloudDataSource(private val service: JokeService) : CloudDataSource {

    override suspend fun getJoke(): Result<JokeServerModel, ErrorType> =
        try {
            val result: JokeServerModel = service.getJoke().execute().body()!!
            Log.d("threadLogTag", "get joke cloud current thread ${Thread.currentThread().name}")
            Result.Success(result)
        } catch (e: Exception) {
            val errorType = if (e is UnknownHostException)
                ErrorType.NO_CONNECTION
            else
                ErrorType.SERVICE_UNAVAILABLE
            Result.Error(errorType)
        }
}