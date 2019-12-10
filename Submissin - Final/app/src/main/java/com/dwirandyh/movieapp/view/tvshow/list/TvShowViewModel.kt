package com.dwirandyh.movieapp.view.tvshow.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwirandyh.movieapp.model.network.NetworkService
import com.dwirandyh.movieapp.model.TvShow
import com.dwirandyh.movieapp.model.network.response.TvListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowViewModel : ViewModel() {

    private val _tvShowMutableLiveData: MutableLiveData<List<TvShow>> = MutableLiveData()
    val tvShowLiveData: LiveData<List<TvShow>>
        get() = _tvShowMutableLiveData


    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        discoverTv()
    }

    fun discoverTv() {
        _isLoading.postValue(true)

        NetworkService.tvShowEndpoint().discoverTv(NetworkService.API_KEY)
            .enqueue(object : Callback<TvListResponse> {
                override fun onFailure(call: Call<TvListResponse>, t: Throwable) {
                    _isLoading.postValue(false)
                }

                override fun onResponse(
                    call: Call<TvListResponse>,
                    response: Response<TvListResponse>
                ) {
                    _isLoading.postValue(false)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            _tvShowMutableLiveData.postValue(it.results)
                        }
                    }
                }

            })
    }

    fun searchTv(query: String) {
        _isLoading.postValue(true)

        NetworkService.tvShowEndpoint().searchTv(NetworkService.API_KEY, query)
            .enqueue(object : Callback<TvListResponse> {
                override fun onFailure(call: Call<TvListResponse>, t: Throwable) {
                    _isLoading.postValue(false)
                }

                override fun onResponse(
                    call: Call<TvListResponse>,
                    response: Response<TvListResponse>
                ) {
                    _isLoading.postValue(false)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            _tvShowMutableLiveData.postValue(it.results)
                        }
                    }
                }
            })
    }
}