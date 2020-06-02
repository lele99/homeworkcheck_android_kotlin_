package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name: String, version:Int) :
    SQLiteOpenHelper(context,name,null,version){
    private val createClass="create table CLASS("+
            "id integer primary key autoincrement,"+
            "class text," +
            "s integer DEFAULT 1)"
    override fun onCreate(db:SQLiteDatabase){
        db.execSQL(createClass)
        Toast.makeText(context,"Create succeeded",Toast.LENGTH_SHORT).show()

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}