package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class oneStudent(val name:String,val imageId:Int)
class oneStudentAdapter(val classList:List<oneStudent>): RecyclerView.Adapter<oneStudentAdapter.ViewHolder>(){
    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        val classImage: ImageView =view.findViewById(R.id.classImage)
        val className: TextView =view.findViewById(R.id.className)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.class_item,parent,false)
        val viewHolder=ViewHolder(view)
        viewHolder.itemView.setOnClickListener{
            val position=viewHolder.adapterPosition
            val fruit=classList[position]
            if(fruit.name=="添加学生"){
                Toast.makeText(parent.context,"you need add a student", Toast.LENGTH_SHORT).show()
                // val extraData=parent.context.getStringExtra("extra_data")
                val intents= Intent(parent.context,MainActivity::class.java)

                //intents.putExtra("extra_data",extraData)
                parent.context.startActivity(intents)



            }
            else if(fruit.name=="删除该学生"){
                val extraData=parent.context.getSharedPreferences("data",0)
                val Class=extraData.getString("Class"," ")
                val Name=extraData.getString("name"," ")
                val msg = "请确认是否删除该学生:"+Name
                AlertDialog.Builder(parent.context)
                    .setMessage(msg)
                    .setTitle("删除该学生")
                    .setPositiveButton("删除", DialogInterface.OnClickListener { dialogInterface, i ->
                        val dbHelper=MyDatabaseHelper(parent.context,"stu.db",1)
                        val db=dbHelper.writableDatabase
                       
                        db.execSQL("DELETE FROM c"+Class+" WHERE name= "+"'"+Name+"'")
                        Toast.makeText(parent.context, "删除成功,请返回", Toast.LENGTH_SHORT).show()

                    })
                    .setNeutralButton("取消", null)
                    .create()
                    .show()


            }
            else {
                //val intents= Intent(parent.context,Studentlist::class.java)
                //parent.context.startActivity(intents)
                //Toast.makeText(parent.context,"进入学生管理页面，添加学生在第一行，删除该班级在最后一行",Toast.LENGTH_SHORT).show()
            }
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
