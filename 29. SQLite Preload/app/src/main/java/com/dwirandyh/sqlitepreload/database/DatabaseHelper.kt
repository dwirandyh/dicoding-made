package com.dwirandyh.sqlitepreload.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.dwirandyh.sqlitepreload.database.DatabaseContract.MahasiwaColumns.Companion.NAMA
import com.dwirandyh.sqlitepreload.database.DatabaseContract.MahasiwaColumns.Companion.NIM
import com.dwirandyh.sqlitepreload.database.DatabaseContract.TABLE_NAME

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbmahasiswa"

        private const val DATABASE_VERSION = 1

        private val CREATE_TABLE_MAHASISWA =
            "create table $TABLE_NAME ($_ID integer primary key autoincrement, $NAMA text not null, $NIM text not null);"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_MAHASISWA)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}