package com.dwirandyh.movieapp.model.network

import com.dwirandyh.movieapp.model.network.response.TvListResponse
import com.dwirandyh.movieapp.model.network.response.TvShowCreditResponse
import com.dwirandyh.movieapp.model.network.response.TvShowVideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowEndpoint {

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
    fun getTvShowVideos(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<TvShowVideoResponse>

    @GET("tv/{id}/credits")
    fun getTvShowCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<TvShowCreditResponse>

    @GET("tv/{id}/recommendations")
    fun getRecommendationTvShow(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<TvListResponse>
}