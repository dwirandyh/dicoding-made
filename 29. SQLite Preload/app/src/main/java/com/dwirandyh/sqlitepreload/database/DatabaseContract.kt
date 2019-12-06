package com.dwirandyh.sqlitepreload.database

import android.provider.BaseColumns

object DatabaseContract {

    var TABLE_NAME = "table_mahasiswa"

    class MahasiwaColumns : BaseColumns {
        companion object {
            const val NAMA = "nama"
            const val NIM = "nim"
        }
    }
}