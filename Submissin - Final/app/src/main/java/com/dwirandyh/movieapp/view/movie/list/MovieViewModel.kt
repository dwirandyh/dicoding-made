package com.dwirandyh.movieapp.view.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwirandyh.movieapp.model.network.NetworkService
import com.dwirandyh.movieapp.model.network.response.MovieListResponse
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

    fun discoverMovie() {
        _isLoading.postValue(true)

        NetworkService.movieEndpoint().discoverMovie(NetworkService.API_KEY)
            .enqueue(object : Callback<MovieListResponse> {
                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    _isLoading.postValue(false)
                }

                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
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

    fun searchMovie(query: String) {
        _isLoading.postValue(false)

        NetworkService.movieEndpoint().searchMovie(NetworkService.API_KEY, query)
            .enqueue(object : Callback<MovieListResponse> {
                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    _isLoading.postValue(false)
                }

                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
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