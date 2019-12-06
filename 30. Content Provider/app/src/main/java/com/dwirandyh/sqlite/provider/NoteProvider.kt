package com.dwirandyh.sqlite.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dwirandyh.sqlite.db.DatabaseContract.AUTHORITY
import com.dwirandyh.sqlite.db.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.dwirandyh.sqlite.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.dwirandyh.sqlite.db.NoteHelper

class NoteProvider : ContentProvider() {

    companion object {

        /*
        Integer yang digunakan sebagai indentifier/pembeda antara select all dengan select by id
         */
        private const val NOTE = 1
        private const val NOTE_ID = 2

        private lateinit var noteHelper: NoteHelper

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)


        /*
        Uri matcher untuk mempermudah identifier dengan menggunakan integer
        misal
        uri com.dwirandyh.sqlite dicocokan dengan integer 1
        uri com.dwirandyh.sqlite/# dicocokan dengan integer 2
         */
        init {
            // content://com.dwirandyh.sqlite/note
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE)

            // content://com.dwirandyh.sqlite/note/id
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", NOTE_ID)

        }
    }

    override fun onCreate(): Boolean {
        noteHelper = NoteHelper.getInstance(context as Context)
        noteHelper.open()
        return true
    }

    /**
     * Method untuk menlakukan query berdasarkan URI yang dikirimkan
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return cursor
     */
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor = when (sUriMatcher.match(uri)) {
            NOTE -> noteHelper.queryAll()
            NOTE_ID -> noteHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }

        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (NOTE) {
            sUriMatcher.match(uri) -> noteHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated: Int = when (NOTE_ID) {
            sUriMatcher.match(uri) -> noteHelper.update(uri.lastPathSegment.toString(), values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (NOTE_ID) {
            sUriMatcher.match(uri) -> noteHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}
