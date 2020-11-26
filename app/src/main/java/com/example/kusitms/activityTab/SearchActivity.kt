package com.example.kusitms.activityTab


import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bolts.Task
import com.example.kusitms.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_activity.*


class SearchActivity : AppCompatActivity() {
    private lateinit var firestore: DatabaseReference
    lateinit var adapter: Search_Adapter_Activity
    val user = Firebase.auth.currentUser
    val uid = user?.uid
    lateinit var arrayList: ArrayList<Data_Activity>
    lateinit var resultList: ArrayList<Data_Activity>
    //var recyclerView: RecyclerView = findViewById(R.id.recyclerView)

    var searchWord: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        firestore = FirebaseDatabase.getInstance().reference
        var recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        var intent = intent
        searchWord = intent.getStringExtra("검색어").toString()
        search_activityName.text = searchWord
        ainfoTab_backBtn.setOnClickListener {
            finish()
        }
        getData()
        //search(searchWord)
//        adapter = Search_Adapter_Activity(resultList)
//        recyclerView.adapter = adapter

    }


    //데이터 가져오기
    fun getData() {
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        //여기가 안됨됨
       db.collection("activity").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.forEach { i ->
                    Log.d("SearchActivity", i.data.values.toString())
                }
            }
        }
    }
}



//    fun search(searchWord: String) {
//        FirebaseFirestore.getInstance().collection("activity").get().addOnSuccessListener {
//                querySnapshot ->
//            for(item in querySnapshot.documents){
//                Toast.makeText(this, "hi",Toast.LENGTH_LONG).show()
//                var data_activity=item.toObject(Data_Activity::class.java)
//                arrayList.add(data_activity!!)
//            }
//        }
//    }

//        firestore.child("activity").addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var activity_field = ArrayList<String>()
//                var activity_participate = ArrayList<String>()
//                var arrayList = ArrayList<Data_Activity>()
//                var datas:Data_Activity
//                for (child: DataSnapshot in snapshot.children) {
//                    var activity_type : String=""
//                    lateinit var activity_field : ArrayList<String>
//                    var activity_content : String=""
//                    var activity_maxPeoplenum : Int=0
//                    var activity_pic_url : String=""
//                    var activity_object : String=""
//                    lateinit var activity_participate : ArrayList<String>
//                    var activity_subject : String=""
//                    var activity_time : String=""
//                    var activity_writer : String=""
//                    var activity_uid : String=""
//
//                    for (grandChild: DataSnapshot in child.children) {
//                        //str+= "child : "+child.key.toString()+" grandChild : "+grandChild.key.toString()+" grateChild : "+grandChild.value.toString()+"\n"
//                            activity_type=grandChild.child("activity_type").value.toString()
//                            activity_field=
//                                grandChild.child("activity_field").value as ArrayList<String>
//                            activity_content=grandChild.child("activity_content").value.toString()
//                            activity_maxPeoplenum=grandChild.child("activity_maxPeoplenum").value.toString().toInt()
//                            activity_pic_url=grandChild.child("activity_pic_url").value.toString()
//                            activity_object=grandChild.child("activity_object").value.toString()
//                            activity_participate=grandChild.child("activity_participate").value as ArrayList<String>
//                            activity_subject=grandChild.child("activity_subject").value.toString()
//                            activity_time=grandChild.child("activity_time").value.toString()
//                            activity_writer=grandChild.child("activity_writer").value.toString()
//                            activity_uid=grandChild.child("activity_uid").value.toString()
//
//                    }
//                    datas= Data_Activity(activity_type, activity_field, activity_content, activity_maxPeoplenum, activity_pic_url,
//                        activity_object, activity_participate, activity_subject, activity_time, activity_writer, activity_uid)
//                    arrayList.add(datas)
//                    Toast.makeText(this@SearchActivity, datas.activity_subject,Toast.LENGTH_LONG).show()
//                }
//                //여기까지 잘됨
//
//                for (i in arrayList) {
//                    if (i.activity_subject.contains(searchWord))
//                        resultList.add(i)
//                }
//            }
//        })


