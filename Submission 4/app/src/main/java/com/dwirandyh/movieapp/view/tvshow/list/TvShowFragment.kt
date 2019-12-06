package com.dwirandyh.movieapp.view.tvshow.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.adapter.TvShowAdapter
import com.dwirandyh.movieapp.model.TvShow
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private lateinit var viewModel: TvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TvShowViewModel::class.java)

        initRecyclerView()

        subscribeUI()
    }

    private fun initRecyclerView() {
        rv_movie.layoutManager = LinearLayoutManager(activity)
        rv_movie.setHasFixedSize(true)
    }

    private fun subscribeUI() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })

        viewModel.tvShowLiveData.observe(viewLifecycleOwner, Observer {
            showMovieInRecyclerView(it)
        })
    }

    private fun showLoading(state: Boolean) {
        progress_bar.visibility = if (state) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

    }

    private fun showMovieInRecyclerView(movieList: List<TvShow>) {
        val adapter = TvShowAdapter(movieList)
        adapter.onItemClick = {
            navigateToMovieDetail(it)
        }

        rv_movie.adapter = adapter
    }


    private fun navigateToMovieDetail(tvShow: TvShow) {
        val directions =
            TvShowFragmentDirections.actionNavTvShowToTvShowDetailFragment(
                tvShow
            )
        view?.findNavController()?.navigate(directions)
    }
}
