package com.example.kusitms.personTab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.kusitms.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.information_person.*
import kotlinx.android.synthetic.main.row_person.*

class Info_Person : AppCompatActivity() {

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    val user = Firebase.auth.currentUser
    val uid = user?.uid
    val username = user?.displayName

    var myRef = FirebaseDatabase.getInstance().reference.child("my_page")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myRef.addValueEventListener(object : ValueEventListener {

            var namuid = intent.getStringExtra("person_uid").toString()


            override fun onDataChange(snapshot: DataSnapshot) {
                pinfoTab_follower.text = snapshot.child(namuid).child("people").child("follower").childrenCount.toString()
                pinfoTab_following.text = snapshot.child(namuid).child("people").child("following").childrenCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                println("failed to read value")
            }
        })
    }

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.information_person)
        init()
        pInfoTab_followbtn.setOnClickListener {
            //내거
            myRef.child(uid.toString()).child("people").child("following").child(intent.getStringExtra("person_writer").toString()).setValue(intent.getStringExtra("person_uid"))
            //그분꺼
            myRef.child(intent.getStringExtra("person_uid").toString()).child("people").child("follower").child(username.toString()).setValue(uid.toString())
        }
        info()


    }

    fun init(){
        pinfoTab_backBtn.setOnClickListener {
            this.finish()
        }
        Glide.with(this).load(R.drawable.user).circleCrop().into(pinfoTab_personimg);
    }

    fun info(){
        pinfoTab_name.text = intent.getStringExtra("person_writer")
        pinfoTab_work.text = intent.getStringExtra("person_work")

        pinfoTab_tag1.text = intent.getStringExtra("person_tag1")
        pinfoTab_tag2.text = intent.getStringExtra("person_tag2")
        pinfoTab_tag3.text = intent.getStringExtra("person_tag3")
        pinfoTab_content.text = intent.getStringExtra("person_content")
        pinfoTab_subject.text = intent.getStringExtra("person_subject")



        var pic_url:String=intent.getStringExtra("person_pic_url").toString()
        getImg(pic_url)
    }



    fun getImg(name : String){
        var imgRef: StorageReference =storageRef.child("images/${name}")
        imgRef.downloadUrl.addOnSuccessListener {
                Uri->
            val imageURL=Uri.toString()
            if(imageURL == "")
                Glide.with(this).load(R.drawable.place_img)
            else {
                Glide.with(this).load(imageURL)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                    .override(360, 170)
                    .into(pinfoTab_personimg);
            }
        }
    }
}