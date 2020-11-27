package com.example.kusitms.personTab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kusitms.R
import com.example.kusitms.personTab.Adapter_Person
import com.example.kusitms.personTab.Data_Person
import com.example.kusitms.personTab.PSearch_Adapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.recyclerView
import kotlinx.android.synthetic.main.fragment_activity.*
import kotlinx.android.synthetic.main.fragment_person.*
import kotlinx.android.synthetic.main.person_search.*

class Search_Person : AppCompatActivity(){
    var searchKeyword =""

    lateinit var PersonAdapter: PSearch_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.person_search)
        init()
    }

    override fun onStop() {
        super.onStop()
        PersonAdapter.stopListening()
    }

    fun init(){
        val secondIntent = getIntent();
        searchKeyword = secondIntent.extras?.get("keyword").toString()
        println("키워드 : " + searchKeyword)
        search_personName.text = searchKeyword

        psearchTab_backBtn.setOnClickListener {
            this.finish()
        }

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )



        initAdapter()
    }

    fun initAdapter(){
        val query = FirebaseDatabase.getInstance().reference
            .child("person").orderByChild("person_writer")
            .startAt(searchKeyword).endAt(searchKeyword+"\uf8ff")

        val option = FirebaseRecyclerOptions.Builder<Data_Person>()
            .setQuery(query) { snapshot ->
                var person_tag = ArrayList<String>()
                for(i in snapshot.child("person_tag").children){
                    person_tag.add(i.value.toString())
                }
                Data_Person(
                    snapshot.child("person_content").value.toString(),
                    snapshot.child("person_subject").value.toString(),
                    person_tag,
                    snapshot.child("person_time").value.toString(),
                    snapshot.child("person_uid").value.toString(),
                    snapshot.child("person_work").value.toString(),
                    snapshot.child("person_writer").value.toString(),
                    snapshot.child("person_pic_url").value.toString()

                )
            }
            .build()

        PersonAdapter = PSearch_Adapter(option)
        recyclerView.adapter =PersonAdapter
        PersonAdapter.startListening()
    }

}