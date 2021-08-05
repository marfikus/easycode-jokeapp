package com.github.marfikus.jokeapp

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseViewModel(
    private val model: Model,
    private val communication: Communication,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    fun getJoke() = viewModelScope.launch(dispatcher) {
        communication.showState(State.Progress)
        model.getJoke().show(communication)
    }

    fun changeJokeStatus() = viewModelScope.launch(dispatcher) {
        model.changeJokeStatus()?.show(communication)
    }

    fun chooseFavorites(favorites: Boolean) {
        model.chooseDataSource(favorites)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<State>) =
        communication.observe(owner, observer)


    sealed class State {
        fun show(progress: ShowView, button: EnableView, textView: ShowText,
                 imageButton: ShowImage
        ) {
            show(progress, button)
            show(textView, imageButton)
        }

        protected open fun show(progress: ShowView, button: EnableView) {}
        protected open fun show(textView: ShowText, imageButton: ShowImage) {}

        object Progress: State() {
            override fun show(progress: ShowView, button: EnableView) {
                progress.show(true)
                button.enable(false)
            }
        }

        data class Initial(val text: String, @DrawableRes val id: Int) : State() {
            override fun show(progress: ShowView, button: EnableView) {
                progress.show(false)
                button.enable(true)
            }

            override fun show(textView: ShowText, imageButton: ShowImage) {
                textView.show(text)
                imageButton.show(id)
            }
        }
    }
}

interface ShowText {
    fun show(text: String)
}

interface ShowImage {
    fun show(@DrawableRes id: Int)
}

interface ShowView {
    fun show(show: Boolean)
}

interface EnableView {
    fun enable(enable: Boolean)
}
