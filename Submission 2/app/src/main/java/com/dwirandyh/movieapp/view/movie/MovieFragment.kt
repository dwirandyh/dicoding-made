package com.dwirandyh.movieapp.view.movie


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dwirandyh.movieapp.adapter.MovieAdapter
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.repository.MovieRepository
import com.dwirandyh.movieapp.view.BaseListFragment

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : BaseListFragment() {

    private lateinit var movieRepository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let {
            movieRepository = MovieRepository(it)
        }
    }


    override fun getAdapter(): RecyclerView.Adapter<*> {
        val adapter = MovieAdapter(movieRepository.getMovieList())
        adapter.onItemClick = {
            navigateToMovieDetail(it)
        }
        return adapter
    }

    private fun navigateToMovieDetail(movie: Movie) {
        val directions = MovieFragmentDirections.actionNavMovieToMovieDetailFragment(movie)
        view?.findNavController()?.navigate(directions)
    }
}
