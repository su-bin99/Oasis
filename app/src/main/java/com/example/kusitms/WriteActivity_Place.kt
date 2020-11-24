package com.example.kusitms

import android.content.ContentValues
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.place_write.*
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity_Place : AppCompatActivity(){
    val user = Firebase.auth.currentUser
    val uid = user?.uid

    var content = ""
    var photo = ""
    var reserveperson = ""
    var review = ""
    var subject = ""
    var concept = ""
    var maxnum = 0
    var type = ""
    var time = ""
    var writer = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.place_write)
        init()
    }

    fun init(){
        place_submitBtn.setOnClickListener{
            content = edit_place_content.text.toString()
            photo = edit_place_photo.text.toString()
            subject = edit_place_subject.text.toString()
            concept = edit_place_concept.text.toString()
            var temp = edit_place_maxnum.text.toString()

            try {
                maxnum = temp.toInt()
            } catch (e: NumberFormatException) {
                Log.d(ContentValues.TAG, temp + "는 숫자가 아님 ");
            }
            type = edit_place_type.text.toString()

            val currentDateTime = Calendar.getInstance().time
            time = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)

            writer = uid.toString()

            insertData(content, photo, reserveperson, review, subject, concept, maxnum, type, time, writer)
        }
    }

    fun insertData(
        place_content : String,
        place_photo : String,
        reserve_person : String,
        place_review :String,
        place_subject : String,
        place_concept : String,
        place_maxnum : Int,
        place_type : String,
        place_time : String,
        place_writer : String
    ){
        var dataRef = FirebaseDatabase.getInstance().reference.child("place").push()

        dataRef.child("place_content").setValue(place_content)
        dataRef.child("place_photo").setValue(place_photo)
        dataRef.child("place_reserve").child("place_review").setValue(place_review)
        dataRef.child("place_reserve").child("reserve_person").setValue(reserve_person)
        dataRef.child("place_subject").setValue(place_subject)
        dataRef.child("place_tag").child("place_concept").setValue(place_concept)
        dataRef.child("place_tag").child("place_maxnum").setValue(place_maxnum)
        dataRef.child("place_tag").child("place_type").setValue(place_type)
        dataRef.child("place_time").setValue(place_time)
        dataRef.child("place_writer").setValue(place_writer)

        val myToast = Toast.makeText(this.applicationContext, "글 작성이 완료되었습니다.", Toast.LENGTH_SHORT)
        myToast.show()

        this.finish()
    }
}