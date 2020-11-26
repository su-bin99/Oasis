package com.example.kusitms.personTab

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kusitms.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_person.*
import kotlinx.android.synthetic.main.fragment_person.view.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment_Person : Fragment() {
    private var root: View? = null
//    var data:ArrayList<Data_Person> = ArrayList<Data_Person>()
    lateinit var adapter : Adapter_Person
//    lateinit var rdb : DatabaseReference
    var findQuary = false

    val user = Firebase.auth.currentUser
    val uid = user?.uid


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_person, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        init()
        adapter.startListening()
    }

    override fun onStop(){
        super.onStop()
        adapter.stopListening()
    }

    fun init() {
        PwriteButton.setOnClickListener{
            val intent = Intent(context,
                WriteActivity_Person::class.java)
            startActivity(intent)
        }

    }

    fun initRecyclerView(){
        root!!.recyclerView.layoutManager = LinearLayoutManager(requireActivity(),
        LinearLayoutManager.VERTICAL, false)

        val query = FirebaseDatabase.getInstance().reference
            .child("person").limitToLast(50)

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

        adapter = Adapter_Person(option)
        recyclerView.adapter =adapter
        adapter.startListening()

    }

}
