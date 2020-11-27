package com.example.kusitms.activityTab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide.init
import com.example.kusitms.R
import com.example.kusitms.mainTab.Adapter_Main
import com.example.kusitms.mainTab.Data_home
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_main.*

class Search_Activity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var searchKeyword =""

    var field : String = "분야"
    var type : String = "유형"
    var target: String = "대상"
    var pNum: String = "명수"

    lateinit var activityAdapter: ASearch_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        init()
    }

    override fun onStop() {
        super.onStop()
        activityAdapter.stopListening()
    }

    fun init(){
        val secondIntent = getIntent();
        searchKeyword = secondIntent.extras?.get("keyword").toString()
        println("키워드 : " + searchKeyword)
        search_activityName.text = searchKeyword

        asearchTab_backBtn.setOnClickListener {
            this.finish()
        }

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        initspinner()
        initAdapter()
    }

    fun initAdapter(){
        val query = FirebaseDatabase.getInstance().reference
            .child("activity").orderByChild("activity_subject")
//            .equalTo("beach")
            .startAt(searchKeyword).endAt(searchKeyword+"\uf8ff")

        val option = FirebaseRecyclerOptions.Builder<Data_Activity>()
            .setQuery(query,
                SnapshotParser { snapshot ->
                    var activity_field = ArrayList<String>()
                    var activity_participate = ArrayList<String>()
                    for (i in snapshot.child("activity_field").children) {
                        activity_field.add(i.value.toString())
                    }
                    for (i in snapshot.child("participate").children) {
                        activity_participate.add(i.value.toString())
                    }
                    Data_Activity(
                        snapshot.child("activity_type").value.toString(),
                        activity_field,
                        snapshot.child("activity_content").value.toString(),
                        snapshot.child("activity_maxPeoplenum").value.toString().toInt(),
                        snapshot.child("activity_pic_url").value.toString(),
                        snapshot.child("activity_object").value.toString(),
                        activity_participate,
                        snapshot.child("activity_subject").value.toString(),
                        snapshot.child("activity_time").value.toString(),
                        snapshot.child("activity_writer").value.toString(),
                        snapshot.child("activity_uid").value.toString()
                    )
                })
            .build()

        activityAdapter = ASearch_Adapter(option)
        recyclerView.adapter = activityAdapter
        activityAdapter.startListening()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun initspinner(){
        this.let {
            ArrayAdapter.createFromResource(
                it, R.array.a_searchFieldOption,android.R.layout.simple_spinner_item
            ).also{adapter ->
                a_field_spinner.adapter = adapter
            }

            ArrayAdapter.createFromResource(
                it, R.array.a_searchPNumOption,android.R.layout.simple_spinner_item
            ).also{adapter ->
                a_pnum_spinner.adapter = adapter
            }

            ArrayAdapter.createFromResource(
                it, R.array.a_searchTypeOption,android.R.layout.simple_spinner_item
            ).also{adapter ->
                a_type_spinner.adapter = adapter
            }

            ArrayAdapter.createFromResource(
                it, R.array.a_searchTargetOption,android.R.layout.simple_spinner_item
            ).also{adapter ->
                a_target_spinner.adapter = adapter
            }
        }
        a_field_spinner.onItemSelectedListener= this
        a_pnum_spinner.onItemSelectedListener = this
        a_type_spinner.onItemSelectedListener= this
        a_target_spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent!!.getItemAtPosition(position).toString()
        when(parent){
            a_field_spinner->  field = selectedItem
            a_target_spinner->  target = selectedItem
            a_pnum_spinner->  pNum = selectedItem
            a_type_spinner->  type = selectedItem

        }
    }
}