package com.bogdanandrei14.androidapp.todo.movie

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.bogdanandrei14.androidapp.auth.data.local.TodoDatabase
import com.bogdanandrei14.androidapp.todo.data.MovieRepository
import com.bogdanandrei14.androidapp.core.TAG
import com.bogdanandrei14.androidapp.todo.data.Movie
import kotlinx.coroutines.launch
import com.bogdanandrei14.androidapp.core.Result

class MovieEditViewModel(application: Application) : AndroidViewModel(application) {
//    private val mutableMovie = MutableLiveData<Movie>().apply { value =
//        Movie("", "", "", "", "", "")
//    }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    val movieRepository: MovieRepository

    init {
        val movieDao = TodoDatabase.getDatabase(application, viewModelScope).movieDao()
        movieRepository = MovieRepository(movieDao)
    }

    fun getMovieById(movieId: String): LiveData<Movie> {
        Log.v(TAG, "getMovieById...")
        return movieRepository.getById(movieId)
    }

    fun saveOrUpdateMovie(movie: Movie) {
        viewModelScope.launch {
            Log.v(TAG, "saveOrUpdateMovie...");
            mutableFetching.value = true
            mutableException.value = null
            val result: Result<Movie>
            if (movie._id.isNotEmpty()) {
                result = movieRepository.update(movie)
            } else {
                result = movieRepository.save(movie)
            }
            when(result) {
                is Result.Success -> {
                    Log.d(TAG, "saveOrUpdateMovie succeeded");
                }
                is Result.Error -> {
                    Log.w(TAG, "saveOrUpdateMovie failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableCompleted.value = true
            mutableFetching.value = false
        }
    }




//    fun loadMovie(movieId: String) {
//        viewModelScope.launch {
//            Log.i(TAG, "loadMovies...")
//            mutableFetching.value = true
//            mutableException.value = null
//            when (val result = MovieRepository.load(movieId)) {
//                is Result.Success -> {
//                    Log.d(TAG, "loadMovie succeeded")
//                    mutableMovie.value = result.data
//                }
//                is Result.Error -> {
//                    Log.w(TAG, "loadMovie failed", result.exception)
//                    mutableException.value = result.exception
//                }
//            }
//            mutableFetching.value = false
//        }
//    }
//
//    fun saveOrUpdateMovie(nume: String, gen: String, an_aparitie: String, durata: String, descriere: String) {
//        viewModelScope.launch {
//            Log.i(TAG, "saveOrUpdateMovie...")
//            val movie = mutableMovie.value ?: return@launch
//            movie.nume = nume
//            movie.gen = gen
//            movie.an_aparitie = an_aparitie
//            movie.durata = durata
//            movie.descriere = descriere
//            mutableFetching.value = true
//            mutableException.value = null
//            val result: Result<Movie>
//            if (movie._id.isNotEmpty()) {
//                result = MovieRepository.update(movie)
//            } else {
//                result = MovieRepository.save(movie)
//            }
//            when (result) {
//                is Result.Success -> {
//                    Log.d(TAG, "saveOrUpdateMovie succeeded");
//                    mutableMovie.value = result.data
//                }
//                is Result.Error -> {
//                    Log.w(TAG, "saveOrUpdateMovie failed", result.exception);
//                    mutableException.value = result.exception
//                }
//            }
//            mutableCompleted.value = true
//            mutableFetching.value = false
//        }
//    }

}