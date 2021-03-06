package com.bogdanandrei14.androidapp.todo.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bogdanandrei14.androidapp.R
import com.bogdanandrei14.androidapp.core.TAG
import com.bogdanandrei14.androidapp.todo.data.Movie
import kotlinx.android.synthetic.main.fragment_movie_edit.*

class MovieEditFragment : Fragment() {

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
    }

    private lateinit var viewModel: MovieEditViewModel
    private var movieId: String? = null
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate-EditFragment")
        arguments?.let {
            if (it.containsKey(MOVIE_ID)) {
                movieId = it.getString(MOVIE_ID).toString()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_edit, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Log.v(TAG, "onViewCreated")
//        movie_nume.setText(movieId)
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG, "onActivityCreated")
        setupViewModel()
        fab.setOnClickListener {
            Log.v(TAG, "save movie")
//            viewModel.saveOrUpdateMovie(movie_nume.text.toString(), movie_gen.text.toString(), movie_an_aparitie.text.toString(), movie_durata.text.toString(), movie_descriere.text.toString())
            val m = movie
            if (m != null) {
                m.nume = movie_nume.text.toString()
                viewModel.saveOrUpdateMovie(m)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MovieEditViewModel::class.java)
        viewModel.fetching.observe(viewLifecycleOwner, { fetching ->
            Log.v(TAG, "update fetching")
            progress.visibility = if (fetching) View.VISIBLE else View.GONE
        })
        viewModel.fetchingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.v(TAG, "update fetching error")
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.completed.observe(viewLifecycleOwner, { completed ->
            if (completed) {
                Log.v(TAG, "completed, navigate back")
                findNavController().popBackStack()
            }
        })
        val id = movieId
        if (id == null) {
            movie = Movie("", "","","","","")
        } else {
            viewModel.getMovieById(id).observe(viewLifecycleOwner, {
                Log.v(TAG, "update movies")
                if (it != null) {
                    movie = it
                    movie_nume.setText(it.nume)
                    movie_gen.setText(it.gen)
                    movie_an_aparitie.setText(it.an_aparitie)
                    movie_durata.setText(it.durata)
                    movie_descriere.setText(it.descriere)
                }
            })
        }
    }

//    private fun setupViewModel() {
//        viewModel = ViewModelProvider(this).get(MovieEditViewModel::class.java)
//        viewModel.movie.observe(viewLifecycleOwner, { movie ->
//            Log.v(TAG, "update movie")
//            movie_nume.setText(movie.nume)
//            movie_gen.setText(movie.gen)
//            movie_an_aparitie.setText(movie.an_aparitie)
//            movie_durata.setText(movie.durata)
//            movie_descriere.setText(movie.descriere)
//        })
//        viewModel.fetching.observe(viewLifecycleOwner, { fetching ->
//            Log.v(TAG, "update fetching")
//            progress.visibility = if (fetching) View.VISIBLE else View.GONE
//        })
//        viewModel.fetchingError.observe(viewLifecycleOwner, { exception ->
//            if (exception != null) {
//                Log.v(TAG, "update fetching error")
//                val message = "Fetching exception ${exception.message}"
//                val parentActivity = activity?.parent
//                if (parentActivity != null) {
//                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
//        viewModel.completed.observe(viewLifecycleOwner, Observer { completed ->
//            if (completed) {
//                Log.v(TAG, "completed navigate back")
//                findNavController().navigateUp()
//            }
//        })
//        val id = movieId
//        if (id != null) {
//            viewModel.loadMovie(id)
//        }
//    }
}