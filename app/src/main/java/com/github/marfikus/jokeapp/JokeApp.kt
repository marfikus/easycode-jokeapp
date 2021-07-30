package com.github.marfikus.jokeapp

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JokeApp : Application() {

    lateinit var viewModel: ViewModel

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        viewModel = ViewModel(
//            BaseModel(TestCacheDataSource(), TestCloudDataSource(), BaseResourceManager(this))
            BaseModel(
                TestCacheDataSource(),
                BaseCloudDataSource(retrofit.create(JokeService::class.java)),
                BaseResourceManager(this)
            )
        )

    }
}