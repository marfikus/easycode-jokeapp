package com.github.marfikus.jokeapp

import androidx.multidex.MultiDexApplication
import com.github.marfikus.jokeapp.data.*
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// MultiDexApplication needed if minSdkVersion < 20
//class JokeApp : Application() {
class JokeApp : MultiDexApplication() {

    lateinit var baseViewModel: BaseViewModel

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

        val cacheDataSource = BaseCacheDataSource(BaseRealmProvider())
        val resourceManager = BaseResourceManager(this)
        val cloudDataSource = BaseCloudDataSource(retrofit.create(JokeService::class.java))
        val repository = BaseJokeRepository(cacheDataSource, cloudDataSource, BaseCachedJoke())
        val interactor = BaseJokeInteractor(repository, JokeFailureFactory(resourceManager))

        baseViewModel = BaseViewModel(interactor, BaseCommunication())

    }


}