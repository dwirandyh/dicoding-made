package com.dwirandyh.movieapp.model.network.response


import com.dwirandyh.movieapp.model.MovieVideo
import com.google.gson.annotations.SerializedName

data class MovieVideoResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<MovieVideo>
)