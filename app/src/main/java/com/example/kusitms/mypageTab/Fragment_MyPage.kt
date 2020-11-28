package com.example.kusitms.mypageTab

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_mypage.*
import com.example.kusitms.R
import com.example.kusitms.SplashActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * A simple [Fragment] subclass.
 */



class Fragment_MyPage : Fragment() {

    private var root: View? = null

    val user = Firebase.auth.currentUser
    val uid = user?.uid

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var myRef = FirebaseDatabase.getInstance().reference.child("my_page").child(uid.toString())



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                my_followerNum.text = snapshot.child("people").child("follower").childrenCount.toString()
                my_followingNum.text = snapshot.child("people").child("following").childrenCount.toString()
//                mypage_name.text = snapshot.child("privacy").child("name").toString()
            }

            override fun onCancelled(error: DatabaseError) {
                println("failed to read value")
            }
        })


        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_mypage, container, false)
        return root

    }

    override fun onResume() {
        super.onResume()
        init()

        write.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            activity?.finish()
            Toast.makeText(this.context, "Logout Successfully!", Toast.LENGTH_SHORT).show();
        }


//        //ui적으로 수정된 것을 나타낼 방법 필요
//        //우선 수정 버튼 누르면 이름 뜨도록 해둠..
//        nameedit.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//                val myData = myPage.child(uid.toString()).child("privacy").child("name")
////                myData.setValue(name.getText().toString())
//
//                myData.addListenerForSingleValueEvent(object : ValueEventListener{
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        var a = snapshot.value.toString()
//                        name.text = a
//                        print(a)
//                        println(name.text)
//                        println("did it")
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        println("failed")
//                    }
//                })
//            }
//        })
    }

    fun init() {
        Glide.with(this).load(R.drawable.hyuna).circleCrop().into(mypage_personalimg);
//        var rDatabase = database.getReference("Activity").child()
    }


}
