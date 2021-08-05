package com.github.marfikus.jokeapp

import android.content.Context
import android.util.AttributeSet

class CorrectButton : androidx.appcompat.widget.AppCompatButton, EnableView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr)

    override fun enable(enable: Boolean) {
        isEnabled = enable
    }
}