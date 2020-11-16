package com.example.kusitms

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    fun init(){
        login.setOnClickListener {
//            val myToast = Toast.makeText(this.applicationContext, "흐아앙", Toast.LENGTH_SHORT)
//            myToast.show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}