package com.dwirandyh.movieapp.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwirandyh.movieapp.model.network.NetworkService
import com.dwirandyh.movieapp.model.network.response.MovieListResponse
import com.dwirandyh.movieapp.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _nowPlayingMovie: MutableLiveData<List<Movie>> = MutableLiveData()
    val nowPlayingMovie: LiveData<List<Movie>>
        get() = _nowPlayingMovie

    private val _trendingMovie: MutableLiveData<List<Movie>> = MutableLiveData()
    val trendingMovie: LiveData<List<Movie>>
        get() = _trendingMovie


    private val _isNowPlayingLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isNowPlayingLoading: LiveData<Boolean>
        get() = _isNowPlayingLoading

    private val _isTrendingLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isTrendingLoading: LiveData<Boolean>
        get() = _isTrendingLoading

    init {
        getNowPlayingMovie()
        getTrendingMovie()
    }

    private fun getNowPlayingMovie() {
        _isNowPlayingLoading.postValue(true)

        NetworkService.movieEndpoint().getNowPlayingMovie(NetworkService.API_KEY)
            .enqueue(object : Callback<MovieListResponse> {
                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    _isNowPlayingLoading.postValue(false)
                }

                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    _isNowPlayingLoading.postValue(false)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            _nowPlayingMovie.postValue(it.results)
                        }
                    }
                }

            })
    }

    private fun getTrendingMovie() {
        _isTrendingLoading.postValue(true)

        NetworkService.movieEndpoint().getTrendingMovie(NetworkService.API_KEY)
            .enqueue(object : Callback<MovieListResponse> {
                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    _isTrendingLoading.postValue(false)
                }

                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    _isTrendingLoading.postValue(false)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            _trendingMovie.postValue(it.results)
                        }
                    }
                }

            })
    }
}