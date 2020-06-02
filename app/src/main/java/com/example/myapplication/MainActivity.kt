package com.example.myapplication

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.android.synthetic.main.activity_main.*
import me.rosuh.filepicker.bean.FileItemBeanImpl
import me.rosuh.filepicker.config.AbstractFileFilter
import me.rosuh.filepicker.config.FilePickerConfig

import me.rosuh.filepicker.config.FilePickerManager
import me.rosuh.filepicker.filetype.RasterImageFileType

import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URLDecoder
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {
    private var x:Int=0
    val aFilter = object : AbstractFileFilter() {
        override fun doFilter(listData: ArrayList<FileItemBeanImpl>): ArrayList<FileItemBeanImpl> {
            return ArrayList(listData.filter { item ->
                // 文件夹或是图片，则被滤出来，然后新建一个 ArrayList 返回给调用者即可
                ((item.isDir) || (item.fileType is RasterImageFileType))
            })
        }}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE), 0)
            }
        }




        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        requestPermissions(permissions,1)


        btn_slect.setOnClickListener{

            FilePickerManager
                .from(this).maxSelectable(1)
                .forResult(FilePickerManager.REQUEST_CODE)
        }
       // val Class=intent.getStringExtra("extra_data")
        val extraData=getSharedPreferences("data",0)
        val Class=extraData.getString("Class"," ")
        btn_read.setOnClickListener{

           Toast.makeText(this, ed_excelpo.getText().toString(), Toast.LENGTH_SHORT).show()
          //  val file = getTempWorkbookFile(tempFolder, "book1.xlsx")
            val dbHelper=MyDatabaseHelper(this,"stu.db",1)
           val db=dbHelper.writableDatabase
            var i=0
            csvReader(){

                charset = "GBK"
            }.open(ed_excelpo.getText().toString()) {
                readAllAsSequence().forEach { row ->
                    //Do something
                    //var est =URLEncoder.encode(row[0],"GBK")
                    var last= URLDecoder.decode(row[0], "UTF-8")
                    //println(row[0]) //[a, b, c]
                    val values1=ContentValues().apply{
                        put("name",last)
                        put("stunumber",row[1])
                    }
                    db.insert("c"+Class.toString(),null,values1)
                    i=i+1

                }
                Toast.makeText(this@MainActivity,"数据添加成功，本次添加"+i+"名同学",Toast.LENGTH_SHORT).show()
            }



        }
        btn_out.setOnClickListener{

            var mList: List<String> = listOf()
            var mMutableList = mList.toMutableList()
            //数据库遍历
            val class1=extraData.getString("Class","")
            val times=extraData.getInt("times",0)
            val db=MyDatabaseHelper(this,"stu.db",1)
            val listing=db.writableDatabase
            val cursor=listing.query("c"+class1,null,null,null,null,null,null,null)
            csvWriter{charset = "GBK"}.open(ed_out.getText().toString()+"/"+class1+"-o.csv"){
            if(cursor.moveToFirst()){
                do {
                    mMutableList = mList.toMutableList()

                        val name = cursor.getString(cursor.getColumnIndex("name"))
                        val number=cursor.getString(cursor.getColumnIndex("stunumber"))

                    mMutableList.add(name)
                    mMutableList.add(number)
                    var i=1
                    do{
                        val s = cursor.getInt(cursor.getColumnIndex("s"+i.toString()))
                        mMutableList.add(s.toString())
                        i=i+1
                    }while(i<=times)
                    writeRow(mMutableList)



                }while(cursor.moveToNext())
            }
            }

        }
        btn_setout.setOnClickListener{
            x=1
            FilePickerManager
                .from(this)
                .maxSelectable(1)
                .filter(object : AbstractFileFilter() {
                    override fun doFilter(listData: ArrayList<FileItemBeanImpl>): ArrayList<FileItemBeanImpl> {
                        return ArrayList(listData.filter { item ->
                            item.isDir
                        })
                    }
                })
                .skipDirWhenSelect(false)
                .setTheme(R.style.FilePickerThemeReply)
                .forResult(FilePickerManager.REQUEST_CODE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FilePickerManager.REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val list = FilePickerManager.obtainData()
                    Toast.makeText(this, list[0], Toast.LENGTH_SHORT).show()
                    // do your work

                    if(x==1){
                        ed_out.setText(list[0])
                        x=0

                    }else{
                        ed_excelpo.setText(list[0])

                    }



                    /*
                    val row1 = listOf("a", "b", "c")
                    val row2 = listOf("d", "e", "f")
                    csvWriter().open(list[0]) {
                        writeRow(row1)
                        writeRow(row2)
                    }

                     */

                } else {
                    Toast.makeText(this, "You didn't choose anything~", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
