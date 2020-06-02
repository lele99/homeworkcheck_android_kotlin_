package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_classlist.*
import kotlinx.android.synthetic.main.activity_studentlist.*

class Studentlist : AppCompatActivity() {
    private var stuclassList=ArrayList<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentlist)

        initCLass()
        val layoutManager= LinearLayoutManager(this)
        recyclerView_student.layoutManager=layoutManager
        val adapter=StudentAdapter(stuclassList)
        recyclerView_student.adapter=adapter


    }
    private fun initCLass() {
        val extraData=getSharedPreferences("data",0)
        Toast.makeText(this,"班级:"+extraData.getString("Class"," "),Toast.LENGTH_SHORT).show()

        stuclassList = ArrayList<Student>()
        stuclassList.add(Student("添加学生或导出记录", R.drawable.apple_pic))
        val db = MyDatabaseHelper(this, "stu.db", 1)
        val listing = db.writableDatabase
        val cursor = listing.query("c"+extraData.getString("Class"," "), null, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val class1 = cursor.getString(cursor.getColumnIndex("name"))
                //val s = cursor.getString(cursor.getColumnIndex("s"))
                stuclassList.add(Student(class1, R.drawable.apple_pic))
            } while (cursor.moveToNext())
        }
        stuclassList.add(Student("删除该班级", R.drawable.apple_pic))
        cursor.close()

    }

    override fun onRestart() {
        super.onRestart()
        initCLass()
        val adapter=StudentAdapter(stuclassList)
        recyclerView_student.adapter=adapter

    }



}
