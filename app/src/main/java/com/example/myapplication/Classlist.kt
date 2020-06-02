package com.example.myapplication


import android.content.Intent
import android.graphics.Insets.add
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.OneShotPreDrawListener.add
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_classlist.*

class Classlist : AppCompatActivity() {
    val TAG:String = "ShareTempActivity"
    private var stuclassList=ArrayList<CLass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classlist)
        initCLass()
        val layoutManager=LinearLayoutManager(this)
       recyclerView.layoutManager=layoutManager
        val adapter=ClassAdapter(stuclassList)
        recyclerView.adapter=adapter
    }
    private fun initCLass(){
        stuclassList=ArrayList<CLass>()
        stuclassList.add(CLass("创建一个新的班级",R.drawable.apple_pic))
        val db=MyDatabaseHelper(this,"stu.db",1)
        val listing=db.writableDatabase
        val cursor=listing.query("CLASS",null,null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do {
                val class1 = cursor.getString(cursor.getColumnIndex("class"))
                val s = cursor.getString(cursor.getColumnIndex("s"))
                stuclassList.add(CLass(class1, R.drawable.apple_pic))
            }while(cursor.moveToNext())
        }
        cursor.close()



    }

    override fun onRestart() {
        super.onRestart()
        initCLass()
        val adapter=ClassAdapter(stuclassList)
        recyclerView.adapter=adapter

    }


}


