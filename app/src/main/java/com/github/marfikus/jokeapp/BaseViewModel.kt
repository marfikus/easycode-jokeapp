package com.github.marfikus.jokeapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BaseViewModel(
    private val model: Model,
    private val communication: Communication
) : ViewModel() {

    fun getJoke() = viewModelScope.launch {
        communication.showData(model.getJoke().getData())
    }

    fun changeJokeStatus() = viewModelScope.launch {
        model.changeJokeStatus()?.let {
            communication.showData(it.getData())
        }
    }

    fun chooseFavorites(favorites: Boolean) {
        model.chooseDataSource(favorites)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<Pair<String, Int>>) =
        communication.observe(owner, observer)
}
