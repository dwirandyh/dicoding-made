package com.dwirandyh.movieapp.view.favorite.movie


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.adapter.MovieAdapter
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.view.movie.list.MovieFragmentDirections
import kotlinx.android.synthetic.main.fragment_favorite_movie.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment(private val onItemClickListener: OnItemClickListener) : Fragment() {

    private lateinit var favoriteMovieViewModel: FavoriteMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteMovieViewModel = ViewModelProvider(this).get(FavoriteMovieViewModel::class.java)

        initRecyclerView()
        subscribeUI()
    }

    private fun subscribeUI() {
        favoriteMovieViewModel.favoriteMovies.observe(viewLifecycleOwner, Observer {
            showMovieInRecyclerView(it)
        })
    }

    private fun initRecyclerView() {
        rv_movie.layoutManager = LinearLayoutManager(context)
        rv_movie.setHasFixedSize(true)
    }

    private fun showMovieInRecyclerView(movieList: List<Movie>) {
        val adapter = MovieAdapter(movieList)
        adapter.onItemClick = {
            onItemClickListener.onItemClick(it)
        }

        rv_movie.adapter = adapter
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }
}
