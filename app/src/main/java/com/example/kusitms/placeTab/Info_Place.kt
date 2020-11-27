package com.example.kusitms.placeTab

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.kusitms.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.information_place.*

class Info_Place : AppCompatActivity() {

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    val user = Firebase.auth.currentUser
    val uid = user?.uid

    var myRef = FirebaseDatabase.getInstance().reference.child("my_page").child(uid.toString()).child("like").child("place")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.information_place)
        init()
        info()
    }

    fun info(){
        plinfoTab_subject.text = intent.getStringExtra("place_subject")
        plinfoTab_content.text = intent.getStringExtra("place_content")
        print(intent.getStringExtra("place_content"))
        plinfoTab_maxnum.text = intent.getStringExtra("place_maxnum").toString()
        plinfoTab_price.text=intent.getStringExtra("place_price").toString()
        placeTag.text = intent.getStringExtra("place_concept")
        plinfoTab_type.text = intent.getStringExtra("place_type")
        plinfoTab_time.text = intent.getStringExtra("place_time")
        plinfoTab_writer.text = intent.getStringExtra("place_writer")

        var pic_url:String=intent.getStringExtra("place_photo").toString()
        getImg(pic_url)
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
            like.isSelected
            var subject = intent.getStringExtra("place_subject")
            var time = intent.getStringExtra("place_time")

            myRef.child(subject.toString()).setValue(time.toString())
        }
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
                    .into(plinfoTab_placeimg);
            }
        }
    }

}
