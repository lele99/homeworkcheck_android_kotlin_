package com.example.myapplication

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
class CLass(val name:String,val imageId:Int)
class ClassAdapter(val classList:List<CLass>): RecyclerView.Adapter<ClassAdapter.ViewHolder>(){
    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        val classImage:ImageView=view.findViewById(R.id.classImage)
        val className:TextView=view.findViewById(R.id.className)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.class_item,parent,false)
        val viewHolder=ViewHolder(view)
        viewHolder.itemView.setOnClickListener{
            val position=viewHolder.adapterPosition
            val fruit=classList[position]
            if(fruit.name=="创建一个新的班级"){
                Toast.makeText(parent.context,"you need add a class",Toast.LENGTH_SHORT).show()
                val intents= Intent(parent.context,Creatclass::class.java)
                parent.context.startActivity(intents)

            }

            else {
                val intents= Intent(parent.context,Studentlist::class.java)
                val editor=parent.context.getSharedPreferences("data",0).edit()
                editor.putString("Class",fruit.name)
                val dbHelper=MyDatabaseHelper(parent.context,"stu.db",1)
                val db=dbHelper.writableDatabase
                //val list=arrayOf<String>()
                //list[0]="class"

                val cursor=db.rawQuery("select * from CLASS WHERE class= "+fruit.name,null)
               cursor.moveToFirst()
                val tm=cursor.getInt(cursor.getColumnIndex("s"))
                editor.putInt("times",tm)
                editor.apply()
                cursor.close()
                parent.context.startActivity(intents)
                Toast.makeText(parent.context,"进入学生管理页面，添加学生在第一行，删除该班级在最后一行",Toast.LENGTH_SHORT).show()}
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val class1=classList[position]
        holder.classImage.setImageResource(class1.imageId)
        holder.className.text=class1.name
    }

    override fun getItemCount()=classList.size

}




