package com.github.marfikus.jokeapp

import android.app.Application
import com.google.gson.Gson

class JokeApp : Application() {

    lateinit var viewModel: ViewModel

    override fun onCreate() {
        super.onCreate()
//        viewModel = ViewModel(TestModel(BaseResourceManager(this)))
        viewModel = ViewModel(BaseModel(BaseJokeService(Gson()), BaseResourceManager(this)))
    }
}