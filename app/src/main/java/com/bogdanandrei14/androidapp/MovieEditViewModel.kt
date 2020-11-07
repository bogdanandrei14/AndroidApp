package com.bogdanandrei14.androidapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieEditViewModel : ViewModel() {
    private val mutableMovie = MutableLiveData<Movie>().apply { value = Movie("", "", "", "", "", "") }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val movie: LiveData<Movie> = mutableMovie
    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    fun loadMovie(movieId: String) {
        viewModelScope.launch {
            Log.i(TAG, "LoadMovie..")
            mutableFetching.value = true
            mutableException.value = null
            try {
                mutableMovie.value = MovieRepository.load(movieId)
                Log.i(TAG, "loadMovie succeeded")
                mutableFetching.value = false
            }catch (e: Exception) {
                Log.w(TAG, "loadMovie failed")
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

    fun saveOrUpdateMovie(nume: String, gen: String, an_aparitie: String, durata: String, descriere: String) {
        viewModelScope.launch {
            Log.i(TAG, "saveOrUpdateMovie...")
            val movie = mutableMovie.value ?: return@launch
            movie.nume = nume
            movie.gen = gen
            movie.an_aparitie = an_aparitie
            movie.durata = durata
            movie.descriere = descriere
            mutableFetching.value = true
            mutableException.value = null
            try {
                if (movie.id.isNotEmpty()) {
                    mutableMovie.value = MovieRepository.update(movie)
                } else {
                    mutableMovie.value = MovieRepository.save(movie)
                }
                Log.i(TAG, "saveOrUpdateMovie succeeded")
                mutableCompleted.value = true
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "saveOrUpdateMovie", e);
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

}