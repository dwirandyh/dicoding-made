package com.dwirandyh.movieapp.view.favorite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.adapter.FavoritePagerAdapter
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.model.TvShow
import com.dwirandyh.movieapp.view.favorite.movie.FavoriteMovieFragment
import com.dwirandyh.movieapp.view.favorite.tvshow.FavoriteTvShowFragment
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTab()
    }

    private fun initTab() {
        val favoritePagerAdapter = FavoritePagerAdapter(childFragmentManager)
        favoritePagerAdapter.addFragment(
            FavoriteMovieFragment(object : FavoriteMovieFragment.OnItemClickListener {
                override fun onItemClick(movie: Movie) {
                    navigateToMovieDetail(movie)
                }
            }),
            resources.getString(R.string.movie)
        )

        favoritePagerAdapter.addFragment(
            FavoriteTvShowFragment(object : FavoriteTvShowFragment.OnItemClickListener {
                override fun onItemClick(tvShow: TvShow) {
                    navigateToTvShowDetail(tvShow)
                }
            }),
            resources.getString(R.string.tv_show)
        )

        view_pager.adapter = favoritePagerAdapter
        tabs.setupWithViewPager(view_pager)
    }

    private fun navigateToMovieDetail(movie: Movie) {
        val direction = FavoriteFragmentDirections.actionNavFavoriteToMovieDetailFragment(movie)
        view?.findNavController()?.navigate(direction)
    }

    private fun navigateToTvShowDetail(tvShow: TvShow) {
        val direction = FavoriteFragmentDirections.actionNavFavoriteToTvShowDetailFragment(tvShow)
        view?.findNavController()?.navigate(direction)
    }


}
