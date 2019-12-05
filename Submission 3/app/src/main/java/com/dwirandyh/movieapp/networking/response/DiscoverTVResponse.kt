package com.dwirandyh.movieapp.networking.response


import com.dwirandyh.movieapp.model.TvShow
import com.google.gson.annotations.SerializedName

data class DiscoverTVResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<TvShow>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)