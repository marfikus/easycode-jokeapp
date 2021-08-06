package com.github.marfikus.jokeapp.domain

import java.io.IOException

class NoConnectionException : IOException()
class ServiceUnavailableException : IOException()
class NoCachedJokesException : IOException()