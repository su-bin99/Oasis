package com.example.kusitms.personTab

import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kusitms.R
import kotlinx.android.synthetic.main.information_person.*

class Info_Person : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.information_person)
        init()
    }

    fun init(){
        pinfoTab_backBtn.setOnClickListener {
            this.finish()
        }
        Glide.with(this).load(R.drawable.user).circleCrop().into(pinfoTab_personimg);
    }
}