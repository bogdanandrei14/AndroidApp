package com.bogdanandrei14.androidapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_movie.view.*

class MovieListAdapter (
    private val fragment: Fragment
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    var movies = emptyList<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged();
        }
    private var onMovieClick: View.OnClickListener;

    init {
        onMovieClick = View.OnClickListener { view ->
            val movie = view.tag as Movie
            fragment.findNavController().navigate(R.id.MovieEditFragment, Bundle().apply {
                putString(MovieEditFragment.MOVIE_ID, movie.id)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_movie, parent, false)
        Log.v(TAG, "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder $position")
        val movie = movies[position]
        holder.itemView.tag = movie
        holder.textViewNume.text = movie.nume
//        holder.textViewGen.text = movie.gen
        holder.itemView.setOnClickListener(onMovieClick)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewNume: TextView = view.nume
//        val textViewGen: TextView = view.gen
    }
}