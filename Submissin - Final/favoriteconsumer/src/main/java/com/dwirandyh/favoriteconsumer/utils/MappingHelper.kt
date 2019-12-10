package com.dwirandyh.favoriteconsumer.utils

import android.database.Cursor
import com.dwirandyh.favoriteconsumer.model.Movie

object MappingHelper {

    fun mapCursorToMovieArrayList(moviesCursor: Cursor): ArrayList<Movie> {
        val movieList = ArrayList<Movie>()

        while (moviesCursor.moveToNext()) {
            val id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow("id"))
            val title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("title"))
            val backdropPath =
                moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("backdrop_path"))
            val firstAirDate =
                moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("first_air_date"))
            val originalLanguage =
                moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("original_language"))
            val originalName =
                moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("original_name"))
            val overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("overview"))
            val popularity =
                moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow("popularity"))
            val posterPath =
                moviesCursor.getString(moviesCursor.getColumnIndexOrThrow("poster_path"))
            val voteAverage =
                moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow("vote_average"))
            val voteCount = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow("vote_count"))
            val movie =
                Movie(
                    id,
                    backdropPath,
                    firstAirDate,
                    arrayListOf(),
                    title,
                    arrayListOf(),
                    originalLanguage,
                    originalName,
                    overview,
                    popularity,
                    posterPath,
                    voteAverage,
                    voteCount
                )
            movieList.add(movie)
        }

        return movieList
    }
}