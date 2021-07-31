package com.github.marfikus.jokeapp

import androidx.multidex.MultiDexApplication
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// MultiDexApplication needed if minSdkVersion < 20
//class JokeApp : Application() {
class JokeApp : MultiDexApplication() {

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
                .name("realm-local-db")
                .build()
        Realm.setDefaultConfiguration(realmConfig)

        viewModel = ViewModel(
//            BaseModel(TestCacheDataSource(), TestCloudDataSource(), BaseResourceManager(this))
            BaseModel(
                BaseCacheDataSource(),
                BaseCloudDataSource(retrofit.create(JokeService::class.java)),
//                TestCloudDataSource(),
                BaseResourceManager(this)
            )
        )

    }


}