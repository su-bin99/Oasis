package com.example.kusitms

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.person_write.*
import java.lang.Character.isDigit
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class WriteActivity_Person : AppCompatActivity() {

    val user = Firebase.auth.currentUser
    val person_uid = user?.uid

    var content = ""
    var subject = ""
    var tag = ""
    var writer = ""
    var uid = ""
    var work = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.person_write)
        init()
    }

    fun init(){
        person_submitBtn.setOnClickListener {
            var tagarray = ArrayList<String>()

            content = edit_person_content.text.toString()
            subject = edit_person_subject.text.toString()
            tag = edit_person_tag.text.toString()
            uid = person_uid.toString()


            tagarray.add(tag)

            val currentDateTime = Calendar.getInstance().time
            var time = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)
            insertData(content, subject, tagarray, time, uid, work, writer)
        }
    }

    fun insertData(
                   person_content : String,
                   person_subject : String,
                   person_tag : ArrayList<String>,
                   person_time : String,
                   person_uid : String,
                   person_work : String,
                   person_writer : String
    ){
        var dataRef = FirebaseDatabase.getInstance().reference.child("person").push()

        dataRef.child("person_content").setValue(person_content)
        dataRef.child("person_subject").setValue(person_subject)
        for( i in 0 until person_tag.size){
            dataRef.child("person_tag").child(i.toString()).setValue(tag)
        }
        dataRef.child("person_time").setValue(person_time)
        dataRef.child("person_uid").setValue(person_uid)
        dataRef.child("person_work").setValue(person_work)
        dataRef.child("person_writer").setValue(person_writer)

        val myToast = Toast.makeText(this.applicationContext, "글 작성이 완료되었습니다.", Toast.LENGTH_SHORT)
        myToast.show()

        this.finish()
    }
}