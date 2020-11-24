package com.example.kusitms

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import kotlinx.android.extensions.*
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_place.*
import kotlinx.android.synthetic.main.fragment_place.view.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment_Place : Fragment() {
    private var root: View? = null
    lateinit var adapter : Adapter_Place
    var findQuery = false

    val user = Firebase.auth.currentUser
    val uid = user?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_place, container, false)
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
        plWriteButton.setOnClickListener{
            val intent = Intent(context, WriteActivity_Place::class.java)
            startActivity(intent)
        }
    }

    fun initRecyclerView(){
        root!!.recyclerView.layoutManager = LinearLayoutManager(requireActivity(),
        LinearLayoutManager.VERTICAL, false)

        val query = FirebaseDatabase.getInstance().reference
            .child("place").limitToLast(50)

        val option = FirebaseRecyclerOptions.Builder<Data_Place>()
            .setQuery(query, {snapshot ->
                Data_Place(
                    snapshot.child("photo_content").value.toString(),
                    snapshot.child("place_photo").value.toString(),
                    snapshot.child("reserve_person").value.toString(),
                    snapshot.child("place_reserve").child("place_review").value.toString(),
                    snapshot.child("place_subject").value.toString(),
                    snapshot.child("place_tag").child("place_concept").value.toString(),
                    snapshot.child("place_tag").child("place_maxnum").value.toString().toInt(),
                    snapshot.child("place_tag").child("place_type").value.toString(),
                    snapshot.child("place_time").value.toString(),
                    snapshot.child("place_writer").value.toString()
                )
            })
            .build()

        adapter = Adapter_Place(option)
        recyclerView.adapter = adapter
        adapter.startListening()
    }
}