package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sure.*

class SureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sure)
        val extraData=getSharedPreferences("data",0)
        val class1=extraData.getString("class","")
        val times=extraData.getInt("times",0)
        val db=MyDatabaseHelper(this,"stu.db",1)
        val listing=db.writableDatabase
        var string=class1+"班以下同学未提交作业第" +
                times+"次作业\n"
        val cursor=listing.query("c"+class1,null,null,null,null,null,null,null)

        if(cursor.moveToFirst()){
            do {

                val s = cursor.getInt(cursor.getColumnIndex("s"+times.toString()))
                if(s==0){
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val number=cursor.getString(cursor.getColumnIndex("stunumber"))
                    string=string+name+"-"+number+"\n"

                }

            }while(cursor.moveToNext())
        }
        cursor.close()
        tx_show.setText(string)

    }

}
