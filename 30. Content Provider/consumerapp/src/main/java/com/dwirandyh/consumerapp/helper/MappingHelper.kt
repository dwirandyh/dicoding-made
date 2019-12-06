package com.dwirandyh.consumerapp.helper

import android.database.Cursor
import com.dwirandyh.consumerapp.db.DatabaseContract
import com.dwirandyh.consumerapp.entity.Note

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor): ArrayList<Note> {
        val notesList = ArrayList<Note>()
        notesCursor.moveToFirst()
        while (notesCursor.moveToNext()) {
            val id =
                notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
            val title =
                notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
            val description =
                notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
            val date =
                notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
            notesList.add(Note(id, title, description, date))
        }
        return notesList
    }

    fun mapCursorToObject(noteCursor: Cursor): Note {
        noteCursor.moveToNext()
        val id =
            noteCursor.getInt(noteCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
        val title =
            noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
        val description =
            noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
        val date =
            noteCursor.getString(noteCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
        return Note(id, title, description, date)
    }
}