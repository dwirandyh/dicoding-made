package com.dwirandyh.intent

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView

    companion object {
        private const val REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMoveActivity: Button = findViewById(R.id.btn_move_activity)
        btnMoveActivity.setOnClickListener {
            moveActivity()
        }

        val btnMoveWithDataActivity: Button = findViewById(R.id.btn_move_activity_data)
        btnMoveWithDataActivity.setOnClickListener {
            moveActivityWithData()
        }

        val btnMoveWithObjectActivity: Button = findViewById(R.id.btn_move_activity_object)
        btnMoveWithObjectActivity.setOnClickListener {
            moveActivityWithObject()
        }

        val btnDialPhone: Button = findViewById(R.id.btn_dial_number)
        btnDialPhone.setOnClickListener {
            dialPhone()
        }

        val btnMoveForResult: Button = findViewById(R.id.btn_move_for_result)
        btnMoveForResult.setOnClickListener {
            moveActivityForResult()
        }
        tvResult = findViewById(R.id.tv_result)
    }

    private fun moveActivity() {
        val moveIntent = Intent(this@MainActivity, MoveActivity::class.java)
        startActivity(moveIntent)
    }

    private fun moveActivityWithData() {
        val moveWithDataIntent = Intent(this@MainActivity, MoveWithDataActivity::class.java)
        moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Name")
        moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 5)
        startActivity(moveWithDataIntent)
    }

    private fun moveActivityWithObject() {
        val person = Person(
            "Dwi Randy H",
            23,
            "dwirandyh.com",
            "Bandar Lampung"
        )

        val moveWithObjectIntent = Intent(this@MainActivity, MoveWithObjectActivity::class.java)
        moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person)
        startActivity(moveWithObjectIntent)
    }

    private fun dialPhone() {
        val phoneNumber = "08999080000"
        val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(dialPhoneIntent)
    }

    private fun moveActivityForResult() {
        val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)
        startActivityForResult(moveForResultIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == MoveForResultActivity.RESULT_CODE) {
                val selectedValue = data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
                tvResult.text = "Hasil : $selectedValue"
            }
        }
    }
}
