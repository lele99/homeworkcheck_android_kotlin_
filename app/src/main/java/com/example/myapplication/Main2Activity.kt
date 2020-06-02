package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val dbHelper=MyDatabaseHelper(this,"stu.db",1)
        dbHelper.writableDatabase
        btn_class.setOnClickListener{
            val intent= Intent(this,Classlist::class.java)
            startActivity(intent)
        }
        homeworcheck.setOnClickListener{
            val intent= Intent(this,putin::class.java)
            startActivity(intent)
        }
    }

}
