package com.dwirandyh.movieapp.model.network

import com.dwirandyh.movieapp.model.network.response.MovieCreditResponse
import com.dwirandyh.movieapp.model.network.response.MovieListResponse
import com.dwirandyh.movieapp.model.network.response.MovieVideoResponse
import com.dwirandyh.movieapp.model.network.response.TvListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvEndpoint {

    @GET("discover/tv")
    fun discoverTv(
        @Query("api_key") apiKey: String
    ): Call<TvListResponse>

    @GET("search/tv")
    fun searchTv(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<TvListResponse>

    @GET("tv/{id}/videos")
    fun getMovieVideos(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieVideoResponse>

    @GET("tv/{id}/credits")
    fun getMovieCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieCreditResponse>

    @GET("tv/{id}/recommendations")
    fun getRecommendationMovie(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieListResponse>
}