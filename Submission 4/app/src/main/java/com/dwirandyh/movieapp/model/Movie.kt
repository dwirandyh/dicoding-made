package com.dwirandyh.movieapp.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorite_movie")
data class Movie(
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0,
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    var firstAirDate: String,
    @Ignore
    @SerializedName("genre_ids")
    var genreIds: List<Int>? = arrayListOf(),
    @SerializedName("title")
    var title: String,
    @Ignore
    @SerializedName("origin_country")
    var originCountry: List<String>? = arrayListOf(),
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    var originalLanguage: String,
    @ColumnInfo(name = "original_name")
    @SerializedName("original_name")
    var originalName: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("popularity")
    var popularity: Double,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    var voteCount: Int
) : Parcelable {
    constructor() : this(0, "", "", arrayListOf(), "", arrayListOf(), "", "", "", 0.0, "", 0.0, 0)
}