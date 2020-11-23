package com.example.kusitms

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
    lateinit var adapter : Adapter_Activity
    var findQuary = false

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
        initRecyclerView()
        init()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    fun init(){
        writeButton.setOnClickListener{
            val intent = Intent(context, WriteActivity_Activity::class.java)
            startActivity(intent)
        }
    }

    fun initRecyclerView() {

        root!!.recyclerView.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.VERTICAL, false)

        val query = FirebaseDatabase.getInstance().reference
            .child("activity") .limitToLast(50)

        val option = FirebaseRecyclerOptions.Builder<Data_Activity>()
            .setQuery(query, SnapshotParser{ snapshot ->
                var activity_concept = ArrayList<String>()
                var activity_field = ArrayList<String>()
                var activity_type = ArrayList<String>()
                var participate = ArrayList<String>()
                for( i in snapshot.child("activity_concept").children){
                    activity_concept.add(i.value.toString())
                }
                for( i in snapshot.child("activity_field").children){
                    activity_field.add(i.value.toString())
                }
                for( i in snapshot.child("activity_type").children){
                    activity_type.add(i.value.toString())
                }
                for( i in snapshot.child("participate").children){
                    participate.add(i.value.toString())
                }
                Log.d(TAG, "activity_id is: " + snapshot.child("activity_id").value.toString());
                Data_Activity(
                    activity_concept, activity_field,
                    snapshot.child("activity_id").value.toString().toInt(),
                    activity_type,
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
        recyclerView.adapter =adapter
        adapter.startListening()
    }
}
