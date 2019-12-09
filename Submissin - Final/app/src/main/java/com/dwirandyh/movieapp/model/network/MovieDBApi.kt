package com.dwirandyh.movieapp.model.network

import com.dwirandyh.movieapp.model.network.response.MovieListResponse
import com.dwirandyh.movieapp.model.network.response.TvListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBApi {

    @GET("discover/movie")
    fun discoverMovie(
        @Query("api_key") apiKey: String
    ): Call<MovieListResponse>

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<MovieListResponse>

    @GET("discover/tv")
    fun discoverTv(
        @Query("api_key") apiKey: String
    ): Call<TvListResponse>

    @GET("search/tv")
    fun searchTv(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<TvListResponse>

    @GET("discover/movie")
    fun getMovieReleasedToday(
        @Query("api_key") apiKey: String,
        @Query("primary_release.gte") dateToday: String,
        @Query("primary_release.lte") dateToday2: String
    ): Call<MovieListResponse>
}