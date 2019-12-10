package com.dwirandyh.tvapp.view.tvshow.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dwirandyh.movieapp.model.Cast
import com.dwirandyh.movieapp.model.TvShow
import com.dwirandyh.movieapp.model.TvShowVideo
import com.dwirandyh.movieapp.model.local.MovieRoomDatabase
import com.dwirandyh.movieapp.model.network.NetworkService
import com.dwirandyh.movieapp.model.network.response.TvListResponse
import com.dwirandyh.movieapp.model.network.response.TvShowCreditResponse
import com.dwirandyh.movieapp.model.network.response.TvShowVideoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRoomDatabase = MovieRoomDatabase.getDatabase(application)

    private val _isFavorite = MutableLiveData<Boolean>(false)
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _tvShowTrailer = MutableLiveData<TvShowVideo>()
    val tvShowTrailer: LiveData<TvShowVideo>
        get() = _tvShowTrailer

    private val _tvShowActors = MutableLiveData<List<Cast>>()
    val tvShowActors: LiveData<List<Cast>>
        get() = _tvShowActors

    private val _recommendationTvShow = MutableLiveData<List<TvShow>>()
    val recommendationTvShow: LiveData<List<TvShow>>
        get() = _recommendationTvShow

    fun addFavorite(tvShow: TvShow) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRoomDatabase.FavoriteTvShowDao().insert(tvShow)
            _isFavorite.postValue(true)
        }
    }

    fun removeFavorite(tvShow: TvShow) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRoomDatabase.FavoriteTvShowDao().delete(tvShow)
            _isFavorite.postValue(false)
        }
    }

    private fun getFavoriteTvById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val tvShow: TvShow? = movieRoomDatabase.FavoriteTvShowDao().getTvShowById(id)
            if (tvShow != null) {
                _isFavorite.postValue(true)
            } else {
                _isFavorite.postValue(false)
            }
        }
    }

    fun getTvDetail(id: Int) {
        getFavoriteTvById(id)

        getTvShowVideos(id)
        getTvActor(id)
        getRecommendationTv(id)
    }

    private fun getTvShowVideos(id: Int) {
        NetworkService.tvShowEndpoint().getTvShowVideos(id, NetworkService.API_KEY)
            .enqueue(object : Callback<TvShowVideoResponse> {
                override fun onFailure(call: Call<TvShowVideoResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<TvShowVideoResponse>,
                    response: Response<TvShowVideoResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val trailer = it.results.filter { tvVideo ->
                                tvVideo.type == "Trailer" && tvVideo.site == "YouTube"
                            }
                            if (trailer.isNotEmpty()) {
                                _tvShowTrailer.postValue(trailer[0])
                            }
                        }
                    }
                }
            })
    }

    private fun getTvActor(id: Int) {
        NetworkService.tvShowEndpoint().getTvShowCredits(id, NetworkService.API_KEY)
            .enqueue(object : Callback<TvShowCreditResponse> {
                override fun onFailure(call: Call<TvShowCreditResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<TvShowCreditResponse>,
                    response: Response<TvShowCreditResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _tvShowActors.postValue(it.cast)
                        }
                    }
                }
            })
    }

    private fun getRecommendationTv(id: Int) {
        NetworkService.tvShowEndpoint().getRecommendationTvShow(id, NetworkService.API_KEY)
            .enqueue(object : Callback<TvListResponse> {
                override fun onFailure(call: Call<TvListResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<TvListResponse>,
                    response: Response<TvListResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _recommendationTvShow.postValue(it.results)
                        }
                    }
                }
            })
    }

}