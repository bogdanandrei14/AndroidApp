package com.bogdanandrei14.androidapp.todo.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.bogdanandrei14.androidapp.auth.data.local.TodoDatabase
import com.bogdanandrei14.androidapp.todo.data.MovieRepository
import com.bogdanandrei14.androidapp.core.TAG
import com.bogdanandrei14.androidapp.todo.data.Movie
import kotlinx.coroutines.launch
import java.lang.Exception
import com.bogdanandrei14.androidapp.core.Result

class MovieListViewModel(application: Application) : AndroidViewModel(application) {

    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val movies: LiveData<List<Movie>>
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    val movieRepository: MovieRepository

    init {
        val movieDao = TodoDatabase.getDatabase(application, viewModelScope).movieDao()
        movieRepository = MovieRepository(movieDao)
        movies = movieRepository.movies
    }

    fun refresh() {
        viewModelScope.launch {
            Log.v(TAG, "refresh...");
            mutableLoading.value = true
            mutableException.value = null
            when (val result = movieRepository.refresh()) {
                is Result.Success -> {
                    Log.d(TAG, "refresh succeeded");
                }
                is Result.Error -> {
                    Log.w(TAG, "refresh failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableLoading.value = false
        }
    }

}






//    private val mutableMovies = MutableLiveData<List<Movie>>().apply { value = emptyList() }
//    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
//    private val mutableException = MutableLiveData<Exception>().apply { value = null }
//
//    val movies: LiveData<List<Movie>> = mutableMovies
//    val loading: LiveData<Boolean> = mutableLoading
//    val loadingError: LiveData<Exception> = mutableException
//
////    fun createMovie(position: Int): Unit {
////        val list = mutableListOf<Movie>()
////        list.addAll(mutableMovies.value!!)
////        list.add(
////            Movie(
////                position.toString(),
////                "Movie " + position,
////                "Actiune",
////                "2020",
////                "90",
////                "Bun"
////            )
////        )
////        mutableMovies.value = list
////    }
//
//    fun loadMovies() {
//        viewModelScope.launch {
//            Log.v(TAG, "loadMovies...");
//            mutableLoading.value = true
//            mutableException.value = null
//            when (val result = MovieRepository.loadAll()) {
//                is Result.Success -> {
//                    Log.d(TAG, "loadProducts succeeded");
//                    mutableMovies.value = result.data
//                }
//                is Result.Error -> {
//                    Log.w(TAG, "loadMovies failed", result.exception);
//                    mutableException.value = result.exception
//                }
//            }
//            mutableLoading.value = false
//        }
//    }
//}