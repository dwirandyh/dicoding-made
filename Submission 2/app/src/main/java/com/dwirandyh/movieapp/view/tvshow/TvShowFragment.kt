package com.dwirandyh.movieapp.view.tvshow


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dwirandyh.movieapp.adapter.TvShowAdapter
import com.dwirandyh.movieapp.model.TvShow
import com.dwirandyh.movieapp.repository.MovieRepository
import com.dwirandyh.movieapp.view.BaseListFragment

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : BaseListFragment() {

    private lateinit var movieRepository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let {
            movieRepository = MovieRepository(it)
        }
    }

    override fun getAdapter(): RecyclerView.Adapter<*> {
        val adapter = TvShowAdapter(movieRepository.getTvShowList())
        adapter.onItemClick = {
            navigateToTvShowDetail(it)
        }
        return adapter
    }

    private fun navigateToTvShowDetail(tvShow: TvShow) {
        val direction = TvShowFragmentDirections.actionNavTvShowToTvShowDetailFragment(tvShow)
        view?.findNavController()?.navigate(direction)
    }
}
