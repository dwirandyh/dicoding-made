package com.dwirandyh.movieapp.networking

import com.dwirandyh.movieapp.networking.response.DiscoverMovieResponse
import com.dwirandyh.movieapp.networking.response.DiscoverTVResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBApi {

    @GET("discover/movie")
    fun discoverMovie(
        @Query("api_key") apiKey: String
    ): Call<DiscoverMovieResponse>

    @GET("discover/tv")
    fun discoverTv(
        @Query("api_key") apiKey: String
    ): Call<DiscoverTVResponse>
}