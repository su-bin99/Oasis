package com.example.kusitms.activityTab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.kusitms.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.information_activity.*
import kotlinx.android.synthetic.main.information_person.*

class Info_Activity : AppCompatActivity() {

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    val user = Firebase.auth.currentUser
    val uid = user?.uid

    var myRef = FirebaseDatabase.getInstance().reference.child("my_page").child(uid.toString()).child("history")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.information_activity)
        init()
        info()
    }

    fun init(){
        ainfoTab_backBtn.setOnClickListener {
            this.finish()
        }
    }

    fun info(){
        ainfoTab_type.text = intent.getStringExtra("activity_type")
        ainfoTab_writer.text = intent.getStringExtra("activity_writer")
        ainfoTab_object.text = intent.getStringExtra("activity_object")
        ainfoTab_maxnum.text = intent.getStringExtra("activity_maxPeoplenum")
        ainfoTab_content.text = intent.getStringExtra("activity_content")

        var pic_url : String=intent.getStringExtra("activity_pic_url").toString()
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
                    .into(ainfoTab_activityimg);
            }
        }
    }
}
