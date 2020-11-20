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
import kotlinx.android.synthetic.main.fragment_activity.*
import kotlinx.android.synthetic.main.fragment_activity.view.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment_Activity : Fragment() {
    private var root: View? = null
    var data:ArrayList<Data_Activity> = ArrayList<Data_Activity>()
    lateinit var adapter : Adapter_Activity

    lateinit var rdb : DatabaseReference
    var findQuary = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_activity, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    fun init() {
        root!!.recyclerView.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("activity_id/info")
        val query = FirebaseDatabase.getInstance().reference
            .child("activity_id/info") .limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<Data_Activity>()
            .setQuery(query, Data_Activity::class.java)
            .build()
        adapter = Adapter_Activity(option)
        root!!.recyclerView.adapter =adapter
        root!!.btn.setOnClickListener(){

        }
    }

//    fun findQueryAdapter(){
//        if(adapter!=null)
//            adapter.stopListening()
//        val query = rdb.orderByChild("info/writer").equalTo(searchText.text.toString())
//        val option = FirebaseRecyclerOptions.Builder<Data_Person>()
//            .setQuery(query, Data_Person::class.java)
//            .build()
//        root!!.recyclerView.adapter = Adapter_Activity(option)
////        adapter.itemClickListener= object:Adapter_Activity.OnItemClickListener{
////            override fun OnItemClick(view: View, position: Int) {
////                pIdEdit.setText(adapter.getItem(position).pId.toString())
////                pNameEdit.setText(adapter.getItem(position).pName)
////                pQuantityEdit.setText(adapter.getItem(position).pQuantity.toString())
////            }
////        }
//        root!!.recyclerView.adapter = adapter
//        adapter.startListening()
//    }
}
