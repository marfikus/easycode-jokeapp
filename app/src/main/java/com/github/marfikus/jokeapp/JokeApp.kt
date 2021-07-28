package com.github.marfikus.jokeapp

import android.app.Application

class JokeApp : Application() {

    lateinit var viewModel: ViewModel

    override fun onCreate() {
        super.onCreate()
//        viewModel = ViewModel(TestModel(BaseResourceManager(this)))
        viewModel = ViewModel(BaseModel(BaseJokeService(), BaseResourceManager(this)))
    }
}