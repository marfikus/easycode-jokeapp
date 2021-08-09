package com.github.marfikus.jokeapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.github.marfikus.jokeapp.JokeApp
import com.github.marfikus.jokeapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseViewModel = (application as JokeApp).baseViewModel
        val actionButton = findViewById<CorrectButton>(R.id.actionButton)
        val textView = findViewById<CorrectTextView>(R.id.textView)
        val progressBar = findViewById<CorrectProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        actionButton.setOnClickListener {
            baseViewModel.getJoke()
        }

        val changeButton = findViewById<CorrectImageButton>(R.id.changeButton)
        changeButton.setOnClickListener {
            baseViewModel.changeJokeStatus()
        }

        baseViewModel.observe(this, { state ->
            state.show(progressBar, actionButton, textView, changeButton)
        })

        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            baseViewModel.chooseFavorites(isChecked)
        }

        // fix: return joke from favorites after closing app
        baseViewModel.chooseFavorites(checkBox.isChecked)
    }
}