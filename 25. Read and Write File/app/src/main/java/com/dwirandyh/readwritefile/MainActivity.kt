package com.dwirandyh.readwritefile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dwirandyh.readwritefile.fileoperation.FileHelper
import com.dwirandyh.readwritefile.fileoperation.FileModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_new.setOnClickListener {
            newFile()
        }

        button_open.setOnClickListener {
            showList()
        }

        button_save.setOnClickListener {
            saveFile()
        }
    }

    private fun newFile() {
        edit_title.setText("")
        edit_file.setText("")
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show()
    }

    private fun showList() {
        val arrayList = ArrayList<String>()
        val path: File = filesDir
        Collections.addAll(arrayList, *path.list() as Array<String>)
        val items = arrayList.toTypedArray<CharSequence>()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih file yang diinginkan")
        builder.setItems(items) { _, item -> loadData(items[item].toString()) }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        edit_title.setText(fileModel.fileName)
        edit_file.setText(fileModel.data)
        Toast.makeText(this, "Loading ${fileModel.fileName} data", Toast.LENGTH_SHORT).show()
    }

    private fun saveFile() {
        when {
            edit_title.text.toString().isEmpty() -> Toast.makeText(
                this,
                "Title harus diisi terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()
            edit_file.text.toString().isEmpty() -> Toast.makeText(
                this,
                "Kontent harus diisi terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()
            else -> {
                val title = edit_title.text.toString()
                val text = edit_file.text.toString()
                val fileModel = FileModel()
                fileModel.fileName = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving " + fileModel.fileName + " file", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
