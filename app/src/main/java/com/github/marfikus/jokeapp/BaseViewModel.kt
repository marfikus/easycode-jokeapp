package com.github.marfikus.jokeapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BaseViewModel(private val model: Model) : ViewModel() {

    val liveData = MutableLiveData<Pair<String, Int>>()

    fun getJoke() = viewModelScope.launch {
        liveData.value = model.getJoke().getData()
    }

    fun changeJokeStatus() = viewModelScope.launch {
        model.changeJokeStatus()?.let {
            liveData.value = it.getData()
        }
    }

    fun chooseFavorites(favorites: Boolean) {
        model.chooseDataSource(favorites)
    }
}
