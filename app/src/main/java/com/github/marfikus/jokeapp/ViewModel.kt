package com.github.marfikus.jokeapp

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModel(private val model: Model) : ViewModel() {

    private var dataCallback: DataCallback? = null

    fun init(callback: DataCallback) {
        this.dataCallback = callback
    }

    fun getJoke() = viewModelScope.launch {
        val uiModel = model.getJoke()
        dataCallback?.let {
            uiModel.map(it)
        }
    }

    fun changeJokeStatus() = viewModelScope.launch {
        val uiModel = model.changeJokeStatus()
        dataCallback?.let {
            uiModel?.map(it)
        }
    }

    fun chooseFavorites(favorites: Boolean) {
        model.chooseDataSource(favorites)
    }

    fun clear() {
        dataCallback = null
    }
}

interface DataCallback {
    fun provideText(text: String)
    fun provideIconRes(@DrawableRes id:Int)
}