package com.example.kusitms.activityTab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide.init
import com.example.kusitms.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_search.*

class Search_Activity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var searchKeyword =""

    var field : String = "분야"
    var type : String = "유형"
    var target: String = "대상"
    var pNum: String = "명수"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        init()
    }

    fun init(){
        val secondIntent = getIntent();
        searchKeyword = secondIntent.extras?.get("keyword").toString()
        println("키워드 : " + searchKeyword)
        search_activityName.text = searchKeyword

        asearchTab_backBtn.setOnClickListener {
            this.finish()
        }

        initspinner()
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