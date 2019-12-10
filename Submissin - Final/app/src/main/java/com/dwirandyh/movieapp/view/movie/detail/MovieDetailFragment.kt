package com.dwirandyh.movieapp.view.movie.detail


import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.adapter.ActorAdapter
import com.dwirandyh.movieapp.adapter.RecommendationMovieAdapter
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.model.MovieVideo
import com.dwirandyh.movieapp.widget.FavoriteWidgetProvider
import kotlinx.android.synthetic.main.fragment_movie_detail.iv_backdrop
import kotlinx.android.synthetic.main.fragment_movie_detail.iv_favorite
import kotlinx.android.synthetic.main.fragment_movie_detail.rb_rating
import kotlinx.android.synthetic.main.fragment_movie_detail.rv_actor
import kotlinx.android.synthetic.main.fragment_movie_detail.rv_recommendation
import kotlinx.android.synthetic.main.fragment_movie_detail.tv_description
import kotlinx.android.synthetic.main.fragment_movie_detail.tv_name
import kotlinx.android.synthetic.main.fragment_movie_detail.tv_rating
import kotlinx.android.synthetic.main.fragment_movie_detail.tv_trailer


/**
 * A simple [Fragment] subclass.
 */
class MovieDetailFragment : Fragment() {

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    private lateinit var movie: Movie
    private var movieTrailer: MovieVideo? = null
    private var isFavorite: Boolean = false


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

        movieDetailViewModel.getMovieDetail(movie.id)


        changeToolbarTitle(movie.title)
        showMovieData()
        initClickHandler()

        initActorRecyclerView()
        initRecommendationRecyclerView()

        subscribeUI()
    }

    private fun showMovieData() {
        val rating = movie.voteAverage / 2

        tv_name.text = movie.title
        tv_description.text = movie.overview
        tv_rating.text = movie.voteAverage.toString()
        rb_rating.rating = rating.toFloat()

        loadPoster(movie.posterPath)
    }

    private fun initClickHandler() {
        iv_favorite.setOnClickListener {
            addFavorite()
        }

        tv_trailer.setOnClickListener {
            openTrailer()
        }
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

        movieDetailViewModel.movieTrailer.observe(viewLifecycleOwner, Observer {
            movieTrailer = it
        })

        movieDetailViewModel.movieActor.observe(viewLifecycleOwner, Observer {
            rv_actor.adapter = ActorAdapter(it)
        })

        movieDetailViewModel.recommendationMovies.observe(viewLifecycleOwner, Observer {
            val adapter = RecommendationMovieAdapter(it)
            adapter.onItemClick = { movie ->
                navigateToMovieDetail(movie)
            }
            rv_recommendation.adapter = adapter
        })
    }

    private fun addFavorite() {
        if (isFavorite) {
            movieDetailViewModel.removeFavorite(movie)
        } else {
            movieDetailViewModel.addFavorite(movie)
        }

        updateWidget()
    }

    private fun updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(
                requireContext(),
                FavoriteWidgetProvider::class.java
            )
        )
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)
    }

    private fun loadPoster(posterPath: String) {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500$posterPath")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv_backdrop)
    }

    private fun openTrailer() {
        movieTrailer?.let {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=${it.key}"))
            startActivity(intent)
        }
    }

    private fun initActorRecyclerView() {
        rv_actor.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_actor.setHasFixedSize(true)
    }

    private fun initRecommendationRecyclerView() {
        rv_recommendation.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_recommendation.setHasFixedSize(true)
    }

    private fun changeToolbarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    private fun navigateToMovieDetail(movie: Movie) {
        val directions = MovieDetailFragmentDirections.actionMovieDetailFragmentSelf(movie)
        view?.findNavController()?.navigate(directions)
    }

}
