package com.dwirandyh.movieapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow(
    val posterUrl: String,
    val name: String,
    val creator: String,
    val episode: Int,
    val rating: Float,
    val ratingStar: Float,
    val description: String
) : Parcelable