package com.example.kusitms.personTab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kusitms.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.person_write.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WriteActivity_Person : AppCompatActivity() {

    val user = Firebase.auth.currentUser
    val uid = user?.uid

    var content = ""
    var subject = ""
    var tag = ""
    var writer = ""


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
            writer = uid.toString()

            tagarray.add(tag)

            val currentDateTime = Calendar.getInstance().time
            var time = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)
            insertData(writer,content, time, subject, tagarray)
        }
    }

    fun insertData(person_writer : String,
        person_content : String,
        person_time : String,
        person_subject : String,
        person_tag : ArrayList<String>
    ){
        var dataRef = FirebaseDatabase.getInstance().reference.child("person").push()

        for( i in 0 until person_tag.size){
            dataRef.child("person_tag").child(i.toString()).setValue(tag)
        }

        dataRef.child("person_writer").setValue(person_writer)
        dataRef.child("person_content").setValue(person_content)
        dataRef.child("person_time").setValue(person_time)
        dataRef.child("person_subject").setValue(person_subject)

        val myToast = Toast.makeText(this.applicationContext, "글 작성이 완료되었습니다.", Toast.LENGTH_SHORT)
        myToast.show()

        this.finish()
    }
}