package com.example.kusitms.activityTab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kusitms.R
import kotlinx.android.synthetic.main.information_activity.*

class Info_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.information_activity)
        init()
    }

    fun init(){
        ainfoTab_backBtn.setOnClickListener {
            this.finish()
        }
    }
}
