package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.ChangeJokeStatus
import com.github.marfikus.jokeapp.Joke
import com.github.marfikus.jokeapp.JokeDataFetcher

interface CacheDataSource : JokeDataFetcher, ChangeJokeStatus