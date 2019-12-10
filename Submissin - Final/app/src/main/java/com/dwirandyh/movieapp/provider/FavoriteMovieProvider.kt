package com.dwirandyh.movieapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dwirandyh.movieapp.model.Movie
import com.dwirandyh.movieapp.model.local.MovieRoomDatabase

class FavoriteMovieProvider : ContentProvider() {

    companion object {
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var movieRoomDatabase: MovieRoomDatabase

        init {
            // content://com.dwirandyh.movieapp/favorite_movie
            sUriMatcher.addURI(Movie.AUTHORITY, Movie.TABLE_NAME, 1)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        movieRoomDatabase = MovieRoomDatabase.getDatabase(context as Context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        cursor = movieRoomDatabase.FavoriteMovieDao().loadAllFavoriteMoviesCursor()
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
