package com.example.kusitms

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_write.*
import java.lang.Character.isDigit
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class WriteActivity_Activity : AppCompatActivity() {
    var concept = ""
    var field = ""
    var type = ""
    var subject = ""
    var MaxPeopleNum = 0
    var content = ""
    var pic_url = ""
    var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        init()
    }
    fun init(){
        activity_submitBtn.setOnClickListener {
            var conceptarray = ArrayList<String>()
            var fieldarray = ArrayList<String>()
            var typearray = ArrayList<String>()

            concept = activity_concept.text.toString()
            field = activity_field.text.toString()
            type = activity_type.text.toString()
            subject = activity_subject.text.toString()
            var temp = activity_maxPeopleNum.text.toString()
            content = activity_content.text.toString()
            userId = activity_userId.text.toString()

            conceptarray.add(concept)
            fieldarray.add(field)
            typearray.add(type)

            try {
                MaxPeopleNum = temp.toInt()
            } catch (e: NumberFormatException) {
                Log.d(ContentValues.TAG, temp + "는 숫자가 아님 ");
            }

            val currentDateTime = Calendar.getInstance().time
            var time = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)
            insertData(conceptarray, fieldarray, 0, typearray, content, MaxPeopleNum,
                pic_url, subject, time, userId )
        }
    }

    fun insertData(activity_concept : ArrayList<String>,
                   activity_field : ArrayList<String>,
                   activity_id : Int,
                   activity_type : ArrayList<String>,
                   content : String,
                   maxPeoplenum : Int,
                   pic_url : String,
                   subject : String,
                   time : String,
                   writer : String){
        var dataRef = FirebaseDatabase.getInstance().reference.child("activity").push()

        for(i in 0 until activity_concept.size){
            dataRef.child("activity_concept").child(i.toString()).setValue(content)
        }

        for(i in 0 until activity_field.size){
            dataRef.child("activity_field").child(i.toString()).setValue(content)
        }

        for(i in 0 until activity_type.size){
            dataRef.child("activity_type").child(i.toString()).setValue(content)
        }

        dataRef.child("activity_id").setValue(activity_id)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "activity_id저장성공 ");
            }
            .addOnFailureListener{
                Log.d(ContentValues.TAG, "activity_id저장실패 ");
            }
        dataRef.child("content").setValue(content)
        dataRef.child("maxPeoplenum").setValue(maxPeoplenum)
        dataRef.child("pic_url").setValue(pic_url)
        dataRef.child("subject").setValue(subject)
        dataRef.child("time").setValue(time)
        dataRef.child("writer").setValue(writer)

        val myToast = Toast.makeText(this.applicationContext, "글 작성이 완료되었습니다.", Toast.LENGTH_SHORT)
        myToast.show()

        this.finish()

    }
}