package com.dwirandyh.movieapp.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_tv_show")
@Parcelize
data class TvShow(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    var firstAirDate: String,
    @Ignore
    @SerializedName("genre_ids")
    var genreIds: List<Int>?,
    @Ignore
    @SerializedName("origin_country")
    var originCountry: List<String>?,
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
    constructor() : this(0, "", "", "", null, null, "", "", "", 0.0, "", 0.0, 0)
}