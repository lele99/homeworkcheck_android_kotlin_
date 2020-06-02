package com.example.myapplication

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_creatclass.*

class Creatclass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creatclass)
        val dbHelper=MyDatabaseHelper(this,"stu.db",1)

        btn_creatclass.setOnClickListener(){
            if(ed_class.text.toString()==null){
                Toast.makeText(this,"班级编号不能为空，仅数字", Toast.LENGTH_SHORT).show()
            }else{
            val db=dbHelper.writableDatabase
            val values1=ContentValues().apply {
                put("class",ed_class.text.toString())
            }
            db.insert("CLASS",null,values1)

            val createClass="create table c"+ed_class.text.toString()+" ("+
                    "id integer primary key autoincrement,"+
                    "name text," +
                    "stunumber text," +
                    "s1 integer DEFAULT 0)"

            //val dbHelper_stu=MyDatabaseHelper_stu(this,"stu.db",1,ed_class.text.toString())
            dbHelper.writableDatabase.execSQL(createClass)
                Toast.makeText(this,"班级"+ed_class.text.toString()+"创建成功，请不要再次创建同名班级，会导致错误", Toast.LENGTH_SHORT).show()
            }

        }

    }
}
