package com.github.marfikus.jokeapp

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JokeApp : Application() {

    lateinit var viewModel: ViewModel
    lateinit var realm: Realm

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .name("Realm-db")
                .build()
//        realm = Realm.getDefaultInstance()

        viewModel = ViewModel(
//            BaseModel(TestCacheDataSource(), TestCloudDataSource(), BaseResourceManager(this))
            BaseModel(
                BaseCacheDataSource(realmConfig),
                BaseCloudDataSource(retrofit.create(JokeService::class.java)),
                BaseResourceManager(this)
            )
        )

    }


}