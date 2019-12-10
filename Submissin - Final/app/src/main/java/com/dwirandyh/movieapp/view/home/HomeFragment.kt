package com.dwirandyh.movieapp.view.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwirandyh.movieapp.R
import com.dwirandyh.movieapp.adapter.RecommendationMovieAdapter
import com.dwirandyh.movieapp.model.Movie
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.nowPlayingMovie.observe(viewLifecycleOwner, Observer {
            val adapter = RecommendationMovieAdapter(it)
            adapter.onItemClick = { movie ->
                navigateToMovieDetail(movie)
            }
            rv_now_playing.adapter = adapter
        })

        viewModel.trendingMovie.observe(viewLifecycleOwner, Observer {
            val adapter = RecommendationMovieAdapter(it)
            adapter.onItemClick = { movie ->
                navigateToMovieDetail(movie)
            }
            rv_trending.adapter = adapter
        })

        viewModel.isNowPlayingLoading.observe(viewLifecycleOwner, Observer {
            if (it == true){
                progress_bar_now_playing.visibility = View.VISIBLE
            }else{
                progress_bar_now_playing.visibility = View.INVISIBLE
            }
        })

        viewModel.isTrendingLoading.observe(viewLifecycleOwner, Observer {
            if (it == true){
                progress_bar_trending.visibility = View.VISIBLE
            }else{
                progress_bar_trending.visibility = View.INVISIBLE
            }
        })
    }

    private fun initRecyclerView() {
        rv_now_playing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_now_playing.setHasFixedSize(true)

        rv_trending.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_trending.setHasFixedSize(true)
    }

    private fun navigateToMovieDetail(movie: Movie) {
        val direction = HomeFragmentDirections.actionNavHomeToMovieDetailFragment(movie)
        findNavController().navigate(direction)
    }
}
