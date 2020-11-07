package com.bogdanandrei14.androidapp

import android.nfc.TagLostException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MovieListViewModel :ViewModel() {
    private val mutableMovies = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val movies: LiveData<List<Movie>> = mutableMovies
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    fun createMovie(position: Int): Unit {
        val list = mutableListOf<Movie>()
        list.addAll(mutableMovies.value!!)
        list.add(Movie(position.toString(), "Movie " + position, "Actiune", "2020", "90", "Bun"))
        mutableMovies.value = list
    }

    fun loadMovies() {
        viewModelScope.launch {
            Log.v(TAG, "loadMovies...");
            mutableLoading.value = true
            mutableException.value = null
            try{
                mutableMovies.value = MovieRepository.loadAll()
                Log.d(TAG, "loadMovies succeeded");
                mutableLoading.value = false
            }catch (e: Exception) {
                Log.w(TAG, "loadMovies failed", e);
                mutableException.value = e
                mutableLoading.value = false
            }
        }
    }
}