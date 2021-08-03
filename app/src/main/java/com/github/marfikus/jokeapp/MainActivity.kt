package com.github.marfikus.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = (application as JokeApp).mainViewModel
        val actionButton = findViewById<Button>(R.id.actionButton)
        val textView = findViewById<TextView>(R.id.textView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        actionButton.setOnClickListener {
            actionButton.isEnabled = false
            progressBar.visibility = View.VISIBLE
            mainViewModel.getJoke()
        }

        val changeButton = findViewById<ImageButton>(R.id.changeButton)
        changeButton.setOnClickListener {
            mainViewModel.changeJokeStatus()
        }

        mainViewModel.liveData.observe(this, { (text, drawableResId) ->
            actionButton.isEnabled = true
            progressBar.visibility = View.INVISIBLE
            textView.text = text
            changeButton.setImageResource(drawableResId)
        })

        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            mainViewModel.chooseFavorites(isChecked)
        }

        // fix: return joke from favorites after closing app
        mainViewModel.chooseFavorites(checkBox.isChecked)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}