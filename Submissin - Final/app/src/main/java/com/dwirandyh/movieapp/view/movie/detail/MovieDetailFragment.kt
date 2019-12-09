package com.dwirandyh.movieapp.view.movie.detail


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Movie
import kotlinx.android.synthetic.main.fragment_movie_detail.*

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailFragment : Fragment() {

    private lateinit var movie: Movie
    private var isFavorite: Boolean = false
    private lateinit var movieDetailViewModel: MovieDetailViewModel


    companion object {
        val TAG = MovieDetailFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieDetailViewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie = MovieDetailFragmentArgs.fromBundle(
            arguments as Bundle
        ).movie

        changeToolbarTitle(movie.title)

        val rating = movie.voteAverage / 2

        tv_name.text = movie.title
        tv_description.text = movie.overview
        tv_rating.text = movie.voteAverage.toString()
        rb_rating.rating = rating.toFloat()


        iv_favorite.setOnClickListener {
            addFavorite()
        }

        loadPoster(movie.posterPath)
        movieDetailViewModel.getMovieById(movie.id)

        subscribeUI()
    }

    private fun subscribeUI() {
        movieDetailViewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            isFavorite = it
            context?.let { context ->
                if (isFavorite) {
                    iv_favorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_favorite_fullfill
                        )
                    )
                } else {
                    iv_favorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_favorite_empty
                        )
                    )
                }
            }
        })
    }

    private fun addFavorite() {
        if (isFavorite) {
            movieDetailViewModel.removeFavorite(movie)
        } else {
            movieDetailViewModel.addFavorite(movie)
        }
    }

    private fun loadPoster(posterPath: String) {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500$posterPath")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv_logo)
    }

    private fun changeToolbarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

}
