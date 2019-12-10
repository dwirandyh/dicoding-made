package com.dwirandyh.movieapp.view.movie.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dwirandyh.movieapp.model.Cast
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.model.local.MovieRoomDatabase
import com.dwirandyh.movieapp.model.network.NetworkService
import com.dwirandyh.movieapp.model.MovieVideo
import com.dwirandyh.movieapp.model.network.response.MovieCreditResponse
import com.dwirandyh.movieapp.model.network.response.MovieListResponse
import com.dwirandyh.movieapp.model.network.response.MovieVideoResponse
import retrofit2.Callback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRoomDatabase = MovieRoomDatabase.getDatabase(application)

    private val _isFavorite = MutableLiveData<Boolean>(false)
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _movieTrailer = MutableLiveData<MovieVideo>()
    val movieTrailer: LiveData<MovieVideo>
        get() = _movieTrailer

    private val _movieActors = MutableLiveData<List<Cast>>()
    val movieActor: LiveData<List<Cast>>
        get() = _movieActors

    private val _recommendationMovies = MutableLiveData<List<Movie>>()
    val recommendationMovies: LiveData<List<Movie>>
        get() = _recommendationMovies

    fun addFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRoomDatabase.FavoriteMovieDao().insert(movie)
            _isFavorite.postValue(true)
        }
    }

    fun removeFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRoomDatabase.FavoriteMovieDao().delete(movie)
            _isFavorite.postValue(false)
        }
    }

    private fun getFavoriteMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movie: Movie? = movieRoomDatabase.FavoriteMovieDao().getMovieById(id)
            if (movie != null) {
                _isFavorite.postValue(true)
            } else {
                _isFavorite.postValue(false)
            }
        }
    }

    fun getMovieDetail(id: Int) {
        getFavoriteMovieById(id)

        getMovieVideo(id)
        getMovieActor(id)
        getRecommendationMovie(id)
    }

    private fun getMovieVideo(id: Int) {
        NetworkService.movieDBApi().getMovieVideos(id, NetworkService.API_KEY)
            .enqueue(object : Callback<MovieVideoResponse> {
                override fun onFailure(call: Call<MovieVideoResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<MovieVideoResponse>,
                    response: Response<MovieVideoResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val trailer = it.results.filter { movieVideo ->
                                movieVideo.type == "Trailer" && movieVideo.site == "YouTube"
                            }
                            if (trailer.isNotEmpty()) {
                                _movieTrailer.postValue(trailer[0])
                            }
                        }
                    }
                }
            })
    }

    private fun getMovieActor(id: Int) {
        NetworkService.movieDBApi().getMovieCredits(id, NetworkService.API_KEY)
            .enqueue(object : Callback<MovieCreditResponse> {
                override fun onFailure(call: Call<MovieCreditResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<MovieCreditResponse>,
                    response: Response<MovieCreditResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _movieActors.postValue(it.cast)
                        }
                    }
                }
            })
    }

    private fun getRecommendationMovie(id: Int) {
        NetworkService.movieDBApi().getRecommendationMovie(id, NetworkService.API_KEY)
            .enqueue(object : Callback<MovieListResponse> {
                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _recommendationMovies.postValue(it.results)
                        }
                    }
                }
            })
    }
}