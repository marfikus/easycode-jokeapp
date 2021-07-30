package com.github.marfikus.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = (application as JokeApp).viewModel
        val actionButton = findViewById<Button>(R.id.actionButton)
        val textView = findViewById<TextView>(R.id.textView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        actionButton.setOnClickListener {
            actionButton.isEnabled = false
            progressBar.visibility = View.VISIBLE
            viewModel.getJoke()
        }

        val changeButton = findViewById<ImageButton>(R.id.changeButton)
        changeButton.setOnClickListener {
            viewModel.changeJokeStatus()
        }

        viewModel.init(object : DataCallback {
            override fun provideText(text: String) = runOnUiThread {
                actionButton.isEnabled = true
                progressBar.visibility = View.INVISIBLE
                textView.text = text
            }

            override fun provideIconRes(id: Int) = runOnUiThread {
                changeButton.setImageResource(id)
            }
        })

        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.chooseFavorites(isChecked)
        }
    }

    override fun onDestroy() {
        viewModel.clear()
        super.onDestroy()
    }
}