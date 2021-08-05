package com.github.marfikus.jokeapp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar

class CorrectProgressBar : ProgressBar, ShowView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr)


    override fun show(show: Boolean) {
        visibility = if (show) View.VISIBLE else View.INVISIBLE
    }
}