package com.example.kusitms.placeTab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide.init
import com.example.kusitms.R
import kotlinx.android.synthetic.main.information_place.*

class Info_Place : AppCompatActivity() {

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
}
