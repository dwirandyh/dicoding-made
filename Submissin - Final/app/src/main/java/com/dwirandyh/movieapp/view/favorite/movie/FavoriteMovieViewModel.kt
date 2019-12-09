package com.dwirandyh.movieapp.view.favorite.movie

import android.app.Application
import androidx.lifecycle.*
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.model.local.MovieRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRoomDatabase: MovieRoomDatabase = MovieRoomDatabase.getDatabase(application)

    private val _favoriteMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val favoriteMovies: LiveData<List<Movie>>
        get() = _favoriteMovies

    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            val favoriteList = movieRoomDatabase.FavoriteMovieDao().loadAllFavoriteMovies()
            _favoriteMovies.postValue(favoriteList)
        }
    }

}