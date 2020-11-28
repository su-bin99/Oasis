package com.example.kusitms

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import java.io.Serializable
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;



data class User(
    val uid: String
)


class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var authListener: FirebaseAuth.AuthStateListener
    lateinit var googleSigneInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();

        googleSigneInClient = GoogleSignIn.getClient(this, gso)

//        init()
    }

    override fun onStart() {
        super.onStart()

        google.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                signIn()
            }
        })

        nonMemberBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    /*
        fun init(){
            login.setOnClickListener {
    //            val myToast = Toast.makeText(this.applicationContext, "흐아앙", Toast.LENGTH_SHORT)
    //            myToast.show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    */
    private fun signIn() {
        val signInIntent = googleSigneInClient.signInIntent
        startActivityForResult(signInIntent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)

                FirebaseAuth.getInstance().signInWithCredential(credential)

                val user = Firebase.auth.currentUser
                val uid = user?.uid
                val name = user?.displayName


                val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                val myRef: DatabaseReference = database.getReference("my_page")
//                val testRef : DatabaseReference = myRef.child("userid")
                myRef.child(uid.toString()).child("privacy").child("name").setValue(name.toString())
//                testRef.setValue(uid.toString())

                val myToast = Toast.makeText(this.applicationContext, "흐아앙", Toast.LENGTH_SHORT)
                myToast.show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}