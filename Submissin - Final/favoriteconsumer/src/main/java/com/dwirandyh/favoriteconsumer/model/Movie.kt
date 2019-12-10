package com.dwirandyh.favoriteconsumer.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int = 0,
    var backdropPath: String,
    var firstAirDate: String,
    var genreIds: List<Int>? = arrayListOf(),
    var title: String,
    var originCountry: List<String>? = arrayListOf(),
    var originalLanguage: String,
    var originalName: String,
    var overview: String,
    var popularity: Double,
    var posterPath: String,
    var voteAverage: Double,
    var voteCount: Int
) : Parcelable {

    companion object {
        const val AUTHORITY = "com.dwirandyh.movieapp"
        const val SCHEME = "content"
        const val TABLE_NAME = "favorite_movie"


        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }
}