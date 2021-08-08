package com.github.marfikus.jokeapp

import androidx.annotation.DrawableRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseViewModel(
        private val interactor: JokeInteractor,
        private val communication: Communication,
        private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    fun getJoke() = viewModelScope.launch(dispatcher) {
        communication.showState(State.Progress)
        interactor.getJoke().to().show(communication)
    }

    fun changeJokeStatus() = viewModelScope.launch(dispatcher) {
        if (communication.isState(State.INITIAL))
            interactor.changeFavorites().to().show(communication)
    }

    fun chooseFavorites(favorites: Boolean) {
        interactor.getFavoriteJokes(favorites)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<State>) =
        communication.observe(owner, observer)


    sealed class State {
        protected abstract val type: Int
        companion object {
            const val INITIAL = 0
            const val PROGRESS = 1
            const val FAILED = 2
        }
        fun isType(type: Int): Boolean = this.type == type

        fun show(progress: ShowView, button: EnableView, textView: ShowText,
                 imageButton: ShowImage
        ) {
            show(progress, button)
            show(textView, imageButton)
        }

        protected open fun show(progress: ShowView, button: EnableView) {}
        protected open fun show(textView: ShowText, imageButton: ShowImage) {}

        object Progress: State() {
            override val type: Int
                get() = TODO("Not yet implemented")

            override fun show(progress: ShowView, button: EnableView) {
                progress.show(true)
                button.enable(false)
            }
        }

        abstract class Info(private val text: String, @DrawableRes private val id: Int) : State() {
            override fun show(progress: ShowView, button: EnableView) {
                progress.show(false)
                button.enable(true)
            }

            override fun show(textView: ShowText, imageButton: ShowImage) {
                textView.show(text)
                imageButton.show(id)
            }
        }

        class Initial(private val text: String, @DrawableRes private val id: Int) : Info(text, id) {
            override val type = INITIAL
        }
        class Failed(private val text: String, @DrawableRes private val id: Int) : Info(text, id) {
            override val type = FAILED
        }
    }
}

interface Show<T> {
    fun show(arg: T)
}

interface ShowText : Show<String>

interface ShowImage : Show<Int>

interface ShowView : Show<Boolean>

interface EnableView {
    fun enable(enable: Boolean)
}
