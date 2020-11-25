package com.example.kusitms.activityTab

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRadioButton
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
    var radio_checkedid_before = 0

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
        var lectureRadio: RadioButton = root!!.findViewById(R.id.ac_radio_lecture)
        lectureRadio.isChecked = true
        radio_checkedid_before = R.id.ac_radio_lecture

        AwriteButton.setOnClickListener {
            val intent = Intent(context, WriteActivity_Activity::class.java)
            startActivity(intent)
        }

        ac_radioGroup.setOnCheckedChangeListener { group, checkedId ->
            var thisCheckedBtn: RadioButton = root!!.findViewById(checkedId)
            if(radio_checkedid_before != checkedId){
                var beforeCheckedBtn: RadioButton = root!!.findViewById(radio_checkedid_before)
                beforeCheckedBtn.setTextColor(R.color.darkGray)
            }
            when (checkedId) {
                R.id.ac_radio_lecture -> {
                    findQueryAdapter("강연회")
                    thisCheckedBtn.setTextColor(R.color.pink)
                    radio_checkedid_before = R.id.ac_radio_lecture
                }
                R.id.ac_radio_project -> {
                    findQueryAdapter("프로젝트")
                    thisCheckedBtn.setTextColor(R.color.pink)
                    radio_checkedid_before = R.id.ac_radio_project
                }
                R.id.ac_radio_conference -> {
                    findQueryAdapter("컨퍼런스")
                    thisCheckedBtn.setTextColor(R.color.pink)
                    radio_checkedid_before = R.id.ac_radio_conference
                }
                R.id.ac_radio_study -> {
                    findQueryAdapter("스터디")
                    thisCheckedBtn.setTextColor(R.color.pink)
                    radio_checkedid_before = R.id.ac_radio_study
                }
                R.id.ac_radio_etc -> {
                    findQueryAdapter("기타")
                    thisCheckedBtn.setTextColor(R.color.pink)
                    radio_checkedid_before = R.id.ac_radio_etc
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
                    var activity_concept = ArrayList<String>()
                    var activity_field = ArrayList<String>()
                    var participate = ArrayList<String>()
                    for (i in snapshot.child("activity_concept").children) {
                        activity_concept.add(i.value.toString())
                    }
                    for (i in snapshot.child("activity_field").children) {
                        activity_field.add(i.value.toString())
                    }
                    for (i in snapshot.child("participate").children) {
                        participate.add(i.value.toString())
                    }
                    Log.d(
                        TAG,
                        "activity_id is: " + snapshot.child("activity_id").value.toString()
                    );
                    Data_Activity(
                        activity_concept, activity_field,
                        snapshot.child("activity_id").value.toString().toInt(),
                        snapshot.child("activity_type").value.toString(),
                        snapshot.child("content").value.toString(),
                        snapshot.child("maxPeoplenum").value.toString().toInt(),
                        participate,
                        snapshot.child("pic_url").value.toString(),
                        snapshot.child("subject").value.toString(),
                        snapshot.child("time").value.toString(),
                        snapshot.child("writer").value.toString()
                    )
                })
            .build()

        adapter = Adapter_Activity(option)
        recyclerView.adapter = adapter
        adapter.startListening()
    }

    fun initAdapter() {
        //처음 들어갈 때 그냥 첫번째 라디오버튼 눌린거로 보여주기 위해서 강연로 쿼리,,

//        val query = FirebaseDatabase.getInstance().reference
//            .child("activity").limitToLast(50)

        val query = FirebaseDatabase.getInstance().reference
            .child("activity").orderByChild("activity_type").equalTo("강연회")

        val option = FirebaseRecyclerOptions.Builder<Data_Activity>()
            .setQuery(query,
                SnapshotParser { snapshot ->
                    var activity_concept = ArrayList<String>()
                    var activity_field = ArrayList<String>()
                    var participate = ArrayList<String>()
                    for (i in snapshot.child("activity_concept").children) {
                        activity_concept.add(i.value.toString())
                    }
                    for (i in snapshot.child("activity_field").children) {
                        activity_field.add(i.value.toString())
                    }
                    for (i in snapshot.child("participate").children) {
                        participate.add(i.value.toString())
                    }
                    Log.d(
                        TAG,
                        "activity_id is: " + snapshot.child("activity_id").value.toString()
                    );
                    Data_Activity(
                        activity_concept, activity_field,
                        snapshot.child("activity_id").value.toString().toInt(),
                        snapshot.child("activity_type").value.toString(),
                        snapshot.child("content").value.toString(),
                        snapshot.child("maxPeoplenum").value.toString().toInt(),
                        participate,
                        snapshot.child("pic_url").value.toString(),
                        snapshot.child("subject").value.toString(),
                        snapshot.child("time").value.toString(),
                        snapshot.child("writer").value.toString()
                    )
                })
            .build()

        adapter = Adapter_Activity(option)
        recyclerView.adapter = adapter
        adapter.startListening()
    }
}
