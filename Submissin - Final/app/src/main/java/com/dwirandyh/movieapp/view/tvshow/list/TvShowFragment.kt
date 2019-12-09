package com.dwirandyh.movieapp.view.tvshow.list


import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
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
        // notify that this fragment has options menu
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TvShowViewModel::class.java)

        initRecyclerView()

        subscribeUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    viewModel.discoverTv()
                } else {
                    viewModel.searchTv(query)
                }


                return false
            }
        })
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

        rv_movie.visibility = if (state) {
            View.INVISIBLE
        } else {
            View.VISIBLE
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
