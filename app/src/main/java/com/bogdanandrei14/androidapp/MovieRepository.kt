package com.bogdanandrei14.androidapp

import android.util.Log

object MovieRepository {
    private var cachedMovies: MutableList<Movie>? = null;

    suspend fun loadAll(): List<Movie> {
        Log.i(TAG, "loadAll")
        if (cachedMovies != null) {
            return cachedMovies as List<Movie>;
        }
        cachedMovies = mutableListOf()
        val movies = MovieApi.service.find()
        cachedMovies?.addAll(movies)
        return cachedMovies as List<Movie>
    }

    suspend fun load(movieId: String): Movie {
        Log.i(TAG, "load")
        val movie = cachedMovies?.find { it.id == movieId }
        if (movie != null) {
            return movie
        }
        return MovieApi.service.read(movieId)
    }

    suspend fun save(movie: Movie): Movie {
        Log.i(TAG, "save")
        val createdMovie = MovieApi.service.create(movie)
        cachedMovies?.add(createdMovie)
        return createdMovie
    }

    suspend fun update(movie: Movie): Movie {
        Log.i(TAG, "update")
        val updatedMovie = MovieApi.service.update(movie.id, movie)
        val index = cachedMovies?.indexOfFirst { it.id == movie.id }
        if (index != null) {
            cachedMovies?.set(index, updatedMovie)
        }
        return updatedMovie
    }
}