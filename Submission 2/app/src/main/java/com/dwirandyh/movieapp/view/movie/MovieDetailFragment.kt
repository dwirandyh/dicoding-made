package com.dwirandyh.movieapp.view.movie


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.model.Movie
import kotlinx.android.synthetic.main.fragment_movie_detail.*

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie: Movie = MovieDetailFragmentArgs.fromBundle(arguments as Bundle).movie

        changeToolbarTitle(movie.name)

        tv_name.text = movie.name
        tv_description.text = movie.description
        tv_rating.text = movie.rating.toString()
        rb_rating.rating = movie.ratingStar

        loadPoster(movie.posterUrl)
    }

    private fun loadPoster(url: String) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv_logo)
    }

    private fun changeToolbarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

}
