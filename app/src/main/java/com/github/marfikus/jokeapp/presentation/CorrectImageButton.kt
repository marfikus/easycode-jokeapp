package com.github.marfikus.jokeapp.presentation

import android.content.Context
import android.util.AttributeSet

class CorrectImageButton : androidx.appcompat.widget.AppCompatImageButton, ShowImage {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr)

    override fun show(id: Int) {
        setImageResource(id)
    }
}