package com.github.marfikus.jokeapp

class TestResourceManager : ResourceManager {

    val message = ""

    override fun getString(stringResId: Int) = message
}