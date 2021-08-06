package com.github.marfikus.jokeapp.data

import com.github.marfikus.jokeapp.ErrorType
import com.github.marfikus.jokeapp.JokeDataFetcher

interface CloudDataSource: JokeDataFetcher<JokeServerModel, ErrorType>