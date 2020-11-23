package com.bogdanandrei14.androidapp.todo.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bogdanandrei14.androidapp.R
import com.bogdanandrei14.androidapp.auth.data.AuthRepository
import com.bogdanandrei14.androidapp.core.Constants
import com.bogdanandrei14.androidapp.core.TAG
import kotlinx.android.synthetic.main.fragment_movie_list.*


class MovieListFragment : Fragment() {
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var moviesModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
////        super.onActivityCreated(savedInstanceState)
////        Log.v(TAG, "onActivityCreated")
////        if (!AuthRepository.isLoggedIn) {
////            findNavController().navigate(R.id.LoginFragment)
////            return;
////        }
////        setupMovieList()
////        fab.setOnClickListener {
////            Log.v(TAG, "add new movie")
////            findNavController().navigate(R.id.MovieEditFragment)
////        }
////        logout.setOnClickListener{
////            Log.v(TAG, "logout")
////            AuthRepository.logout()
////            findNavController().navigate(R.id.LoginFragment)
////        }
////    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG, "onActivityCreated")
//        if (!AuthRepository.isLoggedIn) {
//            findNavController().navigate(R.id.LoginFragment)
//            return;
//        }
        Log.v(TAG, "inainte?")
        Log.v(TAG, Constants.instance()?.fetchValueString("token").toString())
        if(Constants.instance()?.fetchValueString("token") == null){
            findNavController().navigate(R.id.LoginFragment)
            Log.v(TAG, "intri macar aici?")
//            return;
        }
        Log.v(TAG, "dupa?")
        setupMovieList()
        fab.setOnClickListener {
            Log.v(TAG, "add new movie")
            findNavController().navigate(R.id.MovieEditFragment)
        }
        logout.setOnClickListener{
            Log.v(TAG, "logout")
            AuthRepository.logout()
            findNavController().navigate(R.id.LoginFragment)
        }
    }

    private fun setupMovieList() {
        movieListAdapter = MovieListAdapter(this)
        movie_list.adapter = movieListAdapter
        moviesModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        moviesModel.movies.observe(viewLifecycleOwner) { movies ->
            Log.v(TAG, "update movies")
            movieListAdapter.movies = movies
        }
        moviesModel.loading.observe(viewLifecycleOwner) { loading ->
            Log.i(TAG, "update loading")
            progress.visibility = if (loading) View.VISIBLE else View.GONE
        }
        moviesModel.loadingError.observe(viewLifecycleOwner) { exception ->
            if (exception != null) {
                Log.i(TAG, "update loading error")
                val message = "Loading exception ${exception.message}"
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        }
        moviesModel.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy")
    }
}

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        Log.v(TAG, "onActivityCreated")
////        if (!AuthRepository.isLoggedIn) {
////            findNavController().navigate(R.id.LoginFragment)
////            return;
////        }
//        Log.v(TAG, Constants.instance()?.fetchValueString("token").toString())
//        if(Constants.instance()?.fetchValueString("token") == null){
//            findNavController().navigate(R.id.LoginFragment)
////            return;
//        }
//        setupMovieList()
//        fab.setOnClickListener {
//            Log.v(TAG, "add new movie")
//            findNavController().navigate(R.id.MovieEditFragment)
//        }
//        logout.setOnClickListener{
//            Log.v(TAG, "logout")
//            AuthRepository.logout()
//            findNavController().navigate(R.id.LoginFragment)
//        }
//    }
//
//    private fun setupMovieList() {
//        movieListAdapter =
//            MovieListAdapter(this)
//        movie_list.adapter = movieListAdapter
//        moviesModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
//        moviesModel.movies.observe(viewLifecycleOwner) { movies ->
//            Log.i(TAG, "update movies")
//            movieListAdapter.movies = movies
//        }
//        moviesModel.loading.observe(viewLifecycleOwner) {loading ->
//            Log.i(TAG, "update laoding")
//            progress.visibility = if (loading) View.VISIBLE else View.GONE
//        }
//        moviesModel.loadingError.observe(viewLifecycleOwner) { exception ->
//            if(exception != null) {
//                Log.i(TAG, "update loading error")
//                val message = "Loading exception ${exception.message}"
//                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
//            }
//        }
//        moviesModel.loadMovies()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.i(TAG, "onDestroy")
//    }
//}