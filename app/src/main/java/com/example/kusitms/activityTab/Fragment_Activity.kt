package com.example.kusitms.activityTab

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kusitms.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_activity.*
import kotlinx.android.synthetic.main.fragment_activity.view.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment_Activity : Fragment() {
    private var root: View? = null
    lateinit var adapter: Adapter_Activity

    val user = Firebase.auth.currentUser
    val uid = user?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_activity, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        initAdapter()
        init()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    @SuppressLint("ResourceAsColor")
    fun init() {
        root!!.recyclerView.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL, false
        )

        AwriteButton.setOnClickListener {
            val intent = Intent(context, WriteActivity_Activity::class.java)
            startActivity(intent)
        }

        ac_radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.ac_radio_lecture -> {
                    findQueryAdapter("강연회")
                    ac_radio_lecture.setTextColor(R.color.pink)
                }
                R.id.ac_radio_project -> {
                    findQueryAdapter("프로젝트")
                    ac_radio_project.setTextColor(R.color.pink)
                }
                R.id.ac_radio_conference -> {
                    findQueryAdapter("컨퍼런스")
                    ac_radio_conference.setTextColor(R.color.pink)
                }
                R.id.ac_radio_study -> {
                    findQueryAdapter("스터디")
                    ac_radio_study.setTextColor(R.color.pink)
                }
                R.id.ac_radio_etc -> {
                    findQueryAdapter("기타")
                    ac_radio_etc.setTextColor(R.color.pink)
                }
            }
        }
    }

    fun findQueryAdapter(activity_type: String) {
        if (adapter != null)
            adapter.stopListening()
        val query = FirebaseDatabase.getInstance().reference
            .child("activity").orderByChild("activity_type").equalTo(activity_type)

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

        adapter = Adapter_Activity(option)
        recyclerView.adapter = adapter
        adapter.startListening()
    }

    fun initAdapter() {

        val query = FirebaseDatabase.getInstance().reference
            .child("activity").limitToLast(50)

        val option = FirebaseRecyclerOptions.Builder<Data_Activity>()
            .setQuery(query,
                SnapshotParser { snapshot ->
                    var activity_field = ArrayList<String>()
                    var activity_participate = ArrayList<String>()
                    for (i in snapshot.child("activity_field").children) {
                        activity_field.add(i.value.toString())
                    }
                    for (i in snapshot.child("activity_participate").children) {
                        activity_participate.add(i.value.toString())
                    }
                    /*
                    Log.d(
                        TAG,
                        "activity_id is: " + snapshot.child("activity_id").value.toString()
                    );

                     */
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

        adapter = Adapter_Activity(option)
        recyclerView.adapter = adapter
        adapter.startListening()
    }

}
