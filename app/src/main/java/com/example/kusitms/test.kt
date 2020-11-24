package com.example.kusitms

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class test : AppCompatActivity() {
    override fun onStart() {
        super.onStart()

        val user = Firebase.auth.currentUser
        val person_uid = user?.uid

        var uid = person_uid.toString()

        var writer = ""

        var myRef = FirebaseDatabase.getInstance().reference.child("my_page")
        myRef.child(uid).child("privacy").child("name").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot?.value.toString()
                writer = value
            }

            override fun onCancelled(error: DatabaseError) {
                println("failed to read value")
            }
        })
    }
}