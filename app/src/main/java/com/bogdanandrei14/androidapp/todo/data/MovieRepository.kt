package com.bogdanandrei14.androidapp.todo.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.bogdanandrei14.androidapp.auth.data.local.MovieDao
import com.bogdanandrei14.androidapp.core.TAG
import com.bogdanandrei14.androidapp.todo.data.remote.MovieApi
import com.bogdanandrei14.androidapp.core.Result

class MovieRepository(private val movieDao: MovieDao) {
    val movies = movieDao.getAll()

    suspend fun refresh(): Result<Boolean> {
        try {
            val movies = MovieApi.service.find()
            for (movie in movies) {
                movieDao.insert(movie)
            }
            return Result.Success(true)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    fun getById(movieId: String): LiveData<Movie> {
        return movieDao.getById(movieId)
    }

    suspend fun save(movie: Movie): Result<Movie> {
        try {
            val createdMovie = MovieApi.service.create(movie)
            movieDao.insert(createdMovie)
            return Result.Success(createdMovie)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun update(movie: Movie): Result<Movie> {
        try {
            val updatedMovie = MovieApi.service.update(movie._id, movie)
            movieDao.update(updatedMovie)
            return Result.Success(updatedMovie)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}

//    private var cachedMovies: MutableList<Movie>? = null;
//
//    suspend fun loadAll(): Result<List<Movie>> {
//        Log.i(TAG, "loadAll")
//        if (cachedMovies != null) {
//            return Result.Success(cachedMovies as List<Movie>)
//        }
//        try {
//            val movies = MovieApi.service.find()
//            cachedMovies = mutableListOf()
//            cachedMovies?.addAll(movies)
//            return Result.Success(cachedMovies as List<Movie>)
//        } catch (e: Exception) {
//            return Result.Error(e)
//        }
//    }
//
//    suspend fun load(movieId: String): Result<Movie> {
//        Log.i(TAG, "load")
//        val movie = cachedMovies?.find { it._id == movieId }
//        if (movie != null) {
//            return Result.Success(movie)
//        }
//        try {
//            return Result.Success(MovieApi.service.read(movieId))
//        } catch (e: Exception) {
//            return Result.Error(e)
//        }
//    }
//
//    suspend fun save(movie: Movie): Result<Movie> {
//        Log.i(TAG, "save")
//        try {
//            val createdMovie = MovieApi.service.create(movie)
//            cachedMovies?.add(createdMovie)
//            return Result.Success(createdMovie)
//        } catch (e: Exception) {
//            return Result.Error(e)
//        }
//    }
//
//    suspend fun update(movie: Movie): Result<Movie> {
//        Log.i(TAG, "update")
//        try {
//            val updatedMovie = MovieApi.service.update(movie._id, movie)
//            val index = cachedMovies?.indexOfFirst { it._id == movie._id }
//            if (index != null) {
//                cachedMovies?.set(index, updatedMovie)
//            }
//            return Result.Success(updatedMovie)
//        } catch (e: Exception) {
//            return Result.Error(e)
//        }
//    }
//}