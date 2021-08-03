package com.github.marfikus.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseViewModel = (application as JokeApp).baseViewModel
        val actionButton = findViewById<Button>(R.id.actionButton)
        val textView = findViewById<TextView>(R.id.textView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        actionButton.setOnClickListener {
            actionButton.isEnabled = false
            progressBar.visibility = View.VISIBLE
            baseViewModel.getJoke()
        }

        val changeButton = findViewById<ImageButton>(R.id.changeButton)
        changeButton.setOnClickListener {
            baseViewModel.changeJokeStatus()
        }

        baseViewModel.liveData.observe(this, { (text, drawableResId) ->
            actionButton.isEnabled = true
            progressBar.visibility = View.INVISIBLE
            textView.text = text
            changeButton.setImageResource(drawableResId)
        })

        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            baseViewModel.chooseFavorites(isChecked)
        }

        // fix: return joke from favorites after closing app
        baseViewModel.chooseFavorites(checkBox.isChecked)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}