package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.domain.NoConnectionException
import com.github.marfikus.jokeapp.domain.ServiceUnavailableException
import java.lang.Exception
import java.net.UnknownHostException

class BaseCloudDataSource(private val service: JokeService) : CloudDataSource {

    override suspend fun getJoke(): JokeDataModel {
        try {
            return service.getJoke().execute().body()!!.to()
        } catch (e: Exception) {
            if (e is UnknownHostException)
                throw NoConnectionException()
            else
                throw ServiceUnavailableException()
        }
    }
}