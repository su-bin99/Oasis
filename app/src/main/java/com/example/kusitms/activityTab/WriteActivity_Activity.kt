package com.example.kusitms.activityTab

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.kusitms.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_write.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WriteActivity_Activity : AppCompatActivity() {

    val user = Firebase.auth.currentUser
    val userid = user?.uid


    var type = ""
    var field = ""
    var content = ""
    var subject = ""
    var maxPeopleNum = 0
    var pic_url = ""
    var act_object = ""
    var participate = ""
    var uid = userid.toString()
    var value = ""

    var myRef = FirebaseDatabase.getInstance().reference.child("my_page").child(uid).child("privacy").child("name")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                value = snapshot.value.toString()
                println("did you")
                println(value)
            }

            override fun onCancelled(error: DatabaseError) {
                println("failed to read value")
            }
        })
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    fun init(){
        activity_submitBtn.setOnClickListener {
            var fieldarray = ArrayList<String>()
            var participatearray = ArrayList<String>()

            field = activity_field.text.toString()
            type = activity_type.text.toString()
            subject = activity_subject.text.toString()
            var temp = activity_maxPeopleNum.text.toString()
            content = activity_content.text.toString()
            participate = activity_participate.text.toString()


            fieldarray.add(field)
            participatearray.add(participate)

            try {
                maxPeopleNum = temp.toInt()
            } catch (e: NumberFormatException) {
                Log.d(ContentValues.TAG, temp + "는 숫자가 아님 ");
            }

            var writer = value

            val currentDateTime = Calendar.getInstance().time
            var time = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)
            insertData(type, fieldarray, content,0, pic_url, act_object, participatearray,
                subject, time, writer, uid)
        }
    }

    fun insertData(activity_type : String,
                   activity_field : ArrayList<String>,
                   activity_content : String,
                   activity_maxPeoplenum : Int,
                   activity_pic_url : String,
                   activity_object : String,
                   activity_participate : ArrayList<String>,
                   activity_subject : String,
                   activity_time : String,
                   activity_writer : String,
                   activity_uid : String){
        var dataRef = FirebaseDatabase.getInstance().reference.child("activity").push()

        dataRef.child("activity_type").setValue(activity_type)
        for(i in 0 until activity_field.size){
            dataRef.child("activity_field").child(i.toString()).setValue(field)
        }
        dataRef.child("activity_content").setValue(activity_content)
/*
        dataRef.child("activity_id").setValue(activity_id)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "activity_id저장성공 ");
            }
            .addOnFailureListener{
                Log.d(ContentValues.TAG, "activity_id저장실패 ");
            }

 */
        dataRef.child("activity_maxPeoplenum").setValue(activity_maxPeoplenum)
        dataRef.child("activity_pic_url").setValue(activity_pic_url)
        dataRef.child("activity_object").setValue(activity_object)
        for(i in 0 until activity_participate.size){
            dataRef.child("activity_participate").child(i.toString()).setValue(activity_participate)
        }

        dataRef.child("activity_subject").setValue(activity_subject)
        dataRef.child("activity_time").setValue(activity_time)
        dataRef.child("activity_writer").setValue(activity_writer)
        dataRef.child("activity_uid").setValue(activity_uid)

        val myToast = Toast.makeText(this.applicationContext, "글 작성이 완료되었습니다.", Toast.LENGTH_SHORT)
        myToast.show()

        this.finish()

    }
}