package com.dwirandyh.movieapp.view.tvshow.detail


import android.os.Bundle
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
import com.dwirandyh.movieapp.model.TvShow
import kotlinx.android.synthetic.main.fragment_tv_show_detail.*


/**
 * A simple [Fragment] subclass.
 */
class TvShowDetailFragment : Fragment() {

    private lateinit var tvShow: TvShow
    private var isFavorite: Boolean = false
    private lateinit var tvShowViewModel: TvShowDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowViewModel = ViewModelProvider(this).get(TvShowDetailViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvShow = TvShowDetailFragmentArgs.fromBundle(
            arguments as Bundle
        ).tvShow

        changeToolbarTitle(tvShow.name)

        val rating = tvShow.voteAverage / 2

        tv_name.text = tvShow.name
        tv_description.text = tvShow.overview
        tv_rating.text = tvShow.voteAverage.toString()
        rb_rating.rating = rating.toFloat()


        iv_favorite.setOnClickListener {
            addFavorite()
        }


        loadPoster(tvShow.posterPath)

        tvShowViewModel.getMovieById(tvShow.id)

        subscribeUI()
    }

    private fun subscribeUI() {
        tvShowViewModel.isFavorite.observe(viewLifecycleOwner, Observer {
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
            tvShowViewModel.removeFavorite(tvShow)
        } else {
            tvShowViewModel.addFavorite(tvShow)
        }
    }


    private fun loadPoster(posterPath: String) {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500$posterPath")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv_backdrop)
    }

    private fun changeToolbarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}
