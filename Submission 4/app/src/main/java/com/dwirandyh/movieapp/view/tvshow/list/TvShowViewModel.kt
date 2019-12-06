package com.dwirandyh.movieapp.view.tvshow.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwirandyh.movieapp.networking.NetworkService
import com.dwirandyh.movieapp.model.TvShow
import com.dwirandyh.movieapp.networking.response.DiscoverTVResponse
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

    private fun discoverTv() {
        _isLoading.postValue(true)

        NetworkService.movieDBApi().discoverTv(NetworkService.API_KEY)
            .enqueue(object : Callback<DiscoverTVResponse> {
                override fun onFailure(call: Call<DiscoverTVResponse>, t: Throwable) {
                    _isLoading.postValue(false)
                }

                override fun onResponse(
                    call: Call<DiscoverTVResponse>,
                    response: Response<DiscoverTVResponse>
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