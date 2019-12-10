package com.dwirandyh.movieapp.model.network

import com.dwirandyh.movieapp.model.network.response.MovieCreditResponse
import com.dwirandyh.movieapp.model.network.response.MovieListResponse
import com.dwirandyh.movieapp.model.network.response.MovieVideoResponse
import com.dwirandyh.movieapp.model.network.response.TvListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieEndpoint {

    @GET("discover/movie")
    fun discoverMovie(
        @Query("api_key") apiKey: String
    ): Call<MovieListResponse>

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<MovieListResponse>


    @GET("discover/movie")
    fun getMovieReleasedToday(
        @Query("api_key") apiKey: String,
        @Query("primary_release.gte") dateToday: String,
        @Query("primary_release.lte") dateToday2: String
    ): Call<MovieListResponse>

    @GET("movie/{id}/videos")
    fun getMovieVideos(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieVideoResponse>

    @GET("movie/{id}/credits")
    fun getMovieCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieCreditResponse>

    @GET("movie/{id}/recommendations")
    fun getRecommendationMovie(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieListResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovie(
        @Query("api_key") apiKey: String
    ): Call<MovieListResponse>

    @GET("trending/movie/week")
    fun getTrendingMovie(
        @Query("api_key") apiKey: String
    ): Call<MovieListResponse>

}