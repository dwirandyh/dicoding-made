package com.dwirandyh.movieapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val posterUrl: String,
    val name: String,
    val director: String,
    val time: Int,
    val rating: Float,
    val ratingStar: Float,
    val description: String
) : Parcelable