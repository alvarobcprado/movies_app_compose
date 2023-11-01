package com.example.moviesapp.domain

abstract class MoviesException(message: String?) : Exception(message)

class NoInternetException(message: String?) : MoviesException(message)

class NoMoviesException(message: String?) : MoviesException(message)

class ServerException(message: String?) : MoviesException(message)
