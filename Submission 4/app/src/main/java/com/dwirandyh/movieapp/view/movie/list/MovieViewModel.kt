package com.dwirandyh.movieapp.view.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwirandyh.movieapp.networking.NetworkService
import com.dwirandyh.movieapp.networking.response.DiscoverMovieResponse
import com.dwirandyh.movieapp.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private val _movieLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val movieLiveData: LiveData<List<Movie>>
        get() = _movieLiveData


    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        discoverMovie()
    }

    private fun discoverMovie() {
        _isLoading.postValue(true)

        NetworkService.movieDBApi().discoverMovie(NetworkService.API_KEY)
            .enqueue(object : Callback<DiscoverMovieResponse> {
                override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                    _isLoading.postValue(false)
                }

                override fun onResponse(
                    call: Call<DiscoverMovieResponse>,
                    response: Response<DiscoverMovieResponse>
                ) {
                    _isLoading.postValue(false)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            _movieLiveData.postValue(it.results)
                        }
                    }
                }

            })
    }
}