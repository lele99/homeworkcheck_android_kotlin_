package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_putin.*

class putin : AppCompatActivity() {
    private  var sa=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_putin)
        val dbHelper=MyDatabaseHelper(this,"stu.db",1)
        val db=dbHelper.writableDatabase
        btn_getclassmsg.setOnClickListener{
            if(ed_getclassmsg.text.toString()!=null){
                val cursor=db.rawQuery("select * from CLASS WHERE class= "+ed_getclassmsg.text.toString(),null)
                cursor.moveToFirst()
                val tms=cursor.getInt(cursor.getColumnIndex("s"))
                ed_tms.setText(tms.toString())
                sa=tms
                cursor.close()
            }
        }
        btn_addnew.setOnClickListener{


            val times=sa+1
            val name=ed_getclassmsg.text.toString()
            db.execSQL("alter table c"+name+" add column s"+times.toString()+" integer DEFAULT 0")
            Toast.makeText(this,times.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this,name, Toast.LENGTH_SHORT).show()

            val values=ContentValues()
            values.put("s",times.toString())
            db.update("CLASS",values,"class = ?", arrayOf(ed_getclassmsg.text.toString()))
            ed_tms.setText(times.toString())


        }
        btn_input.setOnClickListener{
            val stunumber=ed_input.toString()

            val times=ed_tms.text.toString()
            val values=ContentValues()
            values.put("s"+times,1)

            db.update("c"+ed_getclassmsg.text.toString(),values,"stunumber = ?", arrayOf(ed_input.text.toString()))

            ed_input.setText(ed_auto.text.toString())


        }
        btn_name.setOnClickListener{
          //  val stunumber=ed_input.toString()

            val times=ed_tms.text.toString()
            val values=ContentValues()
            values.put("s"+times,1)

            db.update("c"+ed_getclassmsg.text.toString(),values,"name = ?", arrayOf(ed_name.text.toString()))

            ed_input.setText(ed_auto.text.toString())
            ed_name.setText("")

        }
        btn_show.setOnClickListener {

            val editor=this.getSharedPreferences("data",0).edit()
            val times=ed_tms.text.toString()
            editor.putString("class",ed_getclassmsg.text.toString())
            editor.putInt("times",times.toInt())
            editor.apply()
            val intent= Intent(this,SureActivity::class.java)
            startActivity(intent)

        }

    }

}
