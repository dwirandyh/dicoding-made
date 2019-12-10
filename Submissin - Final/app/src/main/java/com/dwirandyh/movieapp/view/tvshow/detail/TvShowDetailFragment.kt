package com.dwirandyh.movieapp.view.tvshow.detail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.adapter.ActorAdapter
import com.dwirandyh.movieapp.adapter.RecommendationTvShowAdapter
import com.dwirandyh.movieapp.model.TvShow
import com.dwirandyh.movieapp.model.TvShowVideo
import com.dwirandyh.movieapp.view.movie.detail.MovieDetailFragmentDirections
import com.dwirandyh.movieapp.view.tvshow.list.TvShowFragmentDirections
import com.dwirandyh.tvapp.view.tvshow.detail.TvShowDetailViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.fragment_tv_show_detail.iv_backdrop
import kotlinx.android.synthetic.main.fragment_tv_show_detail.iv_favorite
import kotlinx.android.synthetic.main.fragment_tv_show_detail.rb_rating
import kotlinx.android.synthetic.main.fragment_tv_show_detail.rv_actor
import kotlinx.android.synthetic.main.fragment_tv_show_detail.rv_recommendation
import kotlinx.android.synthetic.main.fragment_tv_show_detail.tv_description
import kotlinx.android.synthetic.main.fragment_tv_show_detail.tv_name
import kotlinx.android.synthetic.main.fragment_tv_show_detail.tv_rating


/**
 * A simple [Fragment] subclass.
 */
class TvShowDetailFragment : Fragment() {

    private lateinit var tvShowViewModel: TvShowDetailViewModel

    private lateinit var tvShow: TvShow
    private var tvShowTrailer: TvShowVideo? = null
    private var isFavorite: Boolean = false

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

        tvShowViewModel.getTvDetail(tvShow.id)


        changeToolbarTitle(tvShow.name)
        showTvShowData()
        initClickHandler()

        initActorRecyclerView()
        initRecommendationRecyclerView()

        subscribeUI()
    }

    private fun showTvShowData() {
        val rating = tvShow.voteAverage / 2

        tv_name.text = tvShow.name
        tv_description.text = tvShow.overview
        tv_rating.text = tvShow.voteAverage.toString()
        rb_rating.rating = rating.toFloat()

        loadPoster(tvShow.posterPath)
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

        tvShowViewModel.tvShowTrailer.observe(viewLifecycleOwner, Observer {
            tvShowTrailer = it
        })

        tvShowViewModel.tvShowActors.observe(viewLifecycleOwner, Observer {
            rv_actor.adapter = ActorAdapter(it)
        })

        tvShowViewModel.recommendationTvShow.observe(viewLifecycleOwner, Observer {
            val adapter = RecommendationTvShowAdapter(it)
            adapter.onItemClick = { tvShow ->
                navigateToTvShowDetail(tvShow)
            }
            rv_recommendation.adapter = adapter
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

    private fun openTrailer() {
        tvShowTrailer?.let {
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

    private fun navigateToTvShowDetail(tvShow: TvShow) {
        val directions = TvShowDetailFragmentDirections.actionTvShowDetailFragmentSelf(tvShow)
        view?.findNavController()?.navigate(directions)
    }
}
