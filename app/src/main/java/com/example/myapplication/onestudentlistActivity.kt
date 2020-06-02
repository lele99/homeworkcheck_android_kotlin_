package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_classlist.*
import kotlinx.android.synthetic.main.activity_onestudentlist.*
import kotlinx.android.synthetic.main.activity_studentlist.*

class onestudentlistActivity : AppCompatActivity() {
    private var stuclassList=ArrayList<oneStudent>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onestudentlist)
        initCLass()
        val layoutManager= LinearLayoutManager(this)
        recyclerView_onestudent.layoutManager=layoutManager
        val adapter=oneStudentAdapter(stuclassList)
        recyclerView_onestudent.adapter=adapter

    }
    private fun initCLass() {



        val extraData=getSharedPreferences("data",0)
       // Toast.makeText(this,"姓名:"+extraData.getString("name"," "), Toast.LENGTH_SHORT).show()

        stuclassList = ArrayList<oneStudent>()
        //stuclassList.add(oneStudent("添加学生", R.drawable.apple_pic))
        val db = MyDatabaseHelper(this, "stu.db", 1)
        val listing = db.writableDatabase
        val classing="c"+extraData.getString("Class","")

        Log.d("111","$classing")
        Toast.makeText(this,classing,Toast.LENGTH_SHORT).show()
        val naming="'"+extraData.getString("name","")+"'"
        Log.d("111","$naming")
       val cursor=listing.rawQuery("select * from $classing WHERE name= "+naming,null)
        cursor.moveToFirst()
       // val cursor = listing.query("c"+extraData.getString("Class"," "), null, null, null, null, null, null, null)

        var i=extraData.getInt("times",0)
        var j=0
         j= i

       val number= cursor.getString(cursor.getColumnIndex("stunumber"))
        stuclassList.add(oneStudent("学生编号:"+number.toString(), R.drawable.banana_pic))



                do {
                    val class1 = cursor.getInt(cursor.getColumnIndex("s"+i.toString()))
                    //val s = cursor.getString(cursor.getColumnIndex("s"))
                    var combine="第"+i+"次作业未提交"
                    if(class1.toString()=="0"){

                    }else{
                         combine="第"+i+"次作业已提交"
                    }


                    stuclassList.add(oneStudent(combine, R.drawable.apple_pic))

                    i=i-1

                }while(i>0)


        stuclassList.add(oneStudent("删除该学生", R.drawable.banana_pic))
        cursor.close()

    }


}
