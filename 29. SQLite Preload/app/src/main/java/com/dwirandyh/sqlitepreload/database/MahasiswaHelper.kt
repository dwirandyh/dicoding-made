package com.dwirandyh.sqlitepreload.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.dwirandyh.sqlitepreload.model.MahasiswaModel
import com.dwirandyh.sqlitepreload.database.DatabaseContract.MahasiwaColumns.Companion.NAMA
import com.dwirandyh.sqlitepreload.database.DatabaseContract.MahasiwaColumns.Companion.NIM
import com.dwirandyh.sqlitepreload.database.DatabaseContract.TABLE_NAME
import java.sql.SQLException

class MahasiswaHelper(context: Context) {

    private val databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private var INSTANCE: MahasiswaHelper? = null

        fun getInstance(context: Context): MahasiswaHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class) {
                    if (INSTANCE == null) {
                        INSTANCE = MahasiswaHelper(context)
                    }
                }
            }

            return INSTANCE as MahasiswaHelper
        }
    }

    fun getAllData(): ArrayList<MahasiswaModel> {
        val cursor = database.query(TABLE_NAME, null, null, null, null, null, "$_ID ASC", null)
        cursor.moveToFirst()
        val arrayList = ArrayList<MahasiswaModel>()
        var mahasiswaModel: MahasiswaModel
        if (cursor.count > 0) {
            do {
                mahasiswaModel =
                    MahasiswaModel()
                mahasiswaModel.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                mahasiswaModel.name = cursor.getString(cursor.getColumnIndexOrThrow(NAMA))
                mahasiswaModel.nim = cursor.getString(cursor.getColumnIndexOrThrow(NIM))
                arrayList.add(mahasiswaModel)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    fun insert(mahasiswaModel: MahasiswaModel): Long {
        val initialValues = ContentValues()
        initialValues.put(NAMA, mahasiswaModel.name)
        initialValues.put(NIM, mahasiswaModel.nim)
        return database.insert(TABLE_NAME, null, initialValues)
    }

    fun getDataByName(nama: String): ArrayList<MahasiswaModel> {
        val cursor = database.query(
            TABLE_NAME,
            null,
            "$NAMA LIKE ?",
            arrayOf(nama),
            null,
            null,
            "$_ID ASC",
            null
        )
        cursor.moveToFirst()
        val arrayList = ArrayList<MahasiswaModel>()
        var mahasiswaModel: MahasiswaModel
        if (cursor.count > 0) {
            do {
                mahasiswaModel =
                    MahasiswaModel()
                mahasiswaModel.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                mahasiswaModel.name = cursor.getString(cursor.getColumnIndexOrThrow(NAMA))
                mahasiswaModel.nim = cursor.getString(cursor.getColumnIndexOrThrow(NIM))
                arrayList.add(mahasiswaModel)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    //region TRANSACTION for Bulk Insert
    fun beginTransaction() {
        database.beginTransaction()
    }

    fun setTransactionSuccess() {
        database.setTransactionSuccessful()
    }

    fun endTransaction() {
        database.endTransaction()
    }

    fun insertTransaction(mahasiswaModel: MahasiswaModel) {
        val sql = "INSERT INTO $TABLE_NAME ($NAMA, $NIM) VALUES (?, ?)"
        val statement = database.compileStatement(sql)
        statement.bindString(1, mahasiswaModel.name)
        statement.bindString(2, mahasiswaModel.nim)
        statement.execute()
        statement.clearBindings()
    }

    //endregion

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }
}