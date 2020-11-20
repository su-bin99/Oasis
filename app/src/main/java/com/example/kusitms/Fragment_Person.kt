package com.example.kusitms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_person.view.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment_Person : Fragment() {
    private var root: View? = null
    var data:ArrayList<Data_Person> = ArrayList<Data_Person>()

    lateinit var rdb : DatabaseReference
    var findQuary = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_person, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    fun init() {
        root!!.recyclerView.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("Products/items")
        val query = FirebaseDatabase.getInstance().reference
            .child("Products/items") .limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<Data_Person>()
            .setQuery(query, Data_Person::class.java)
            .build()
        root!!.recyclerView.adapter = Adapter_Person(option)

    }




}
