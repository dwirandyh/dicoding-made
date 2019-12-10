package com.dwirandyh.movieapp.model.network.response


import com.dwirandyh.movieapp.model.Cast
import com.dwirandyh.movieapp.model.Crew
import com.google.gson.annotations.SerializedName

data class TvShowCreditResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)