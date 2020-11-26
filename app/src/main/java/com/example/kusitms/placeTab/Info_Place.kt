package com.example.kusitms.placeTab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide.init
import com.example.kusitms.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.information_place.*

class Info_Place : AppCompatActivity() {

    val user = Firebase.auth.currentUser
    val uid = user?.uid

    var myRef = FirebaseDatabase.getInstance().reference.child("my_page").child(uid.toString()).child("like").child("place")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.information_place)
        init()
    }

    fun init(){
        plinfoTab_backBtn.setOnClickListener {
            this.finish()
        }
    }

    override fun onStart() {
        super.onStart()
        like()
    }

    fun like(){
        like.setOnClickListener{
            var subject = intent.getStringExtra("place_subject")
            var time = intent.getStringExtra("place_time")

            myRef.child(subject.toString()).setValue(time.toString())
        }
    }

}
