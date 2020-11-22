package com.example.kusitms

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kukku.Adapter_Activity
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
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
        rdb = FirebaseDatabase.getInstance().getReference()

        root!!.recyclerView.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.VERTICAL, false)
//        getActivityData()
            //reference
            //getReference("activity_id/info")
        insertBtn.setOnClickListener{
            insertData("2")
        }
        getButton.setOnClickListener{
            getActivityData()
        }



//        val query = FirebaseDatabase.getInstance().reference
//            .child("activity/info") .limitToLast(50)
//        val option = FirebaseRecyclerOptions.Builder<Data_Activity>()
//            .setQuery(query, Data_Activity::class.java)
//            .build()

        adapter = Adapter_Activity(data)
        root!!.recyclerView.adapter =adapter
    }

    fun getActivityData() {
        data.clear()
        rdb.child("activity").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for (activity_num: DataSnapshot in snapshot.children) { // 글 하나 접근
                    var data_activity : Data_Activity
                    var contents = Data_activity_contents("", "")
                    var info = Data_activity_info(0, "", "", "")
                    var participates = Data_activity_participates("", "")
                    var tag  = Data_activity_tag("", "", "", 0)

                    for (unit_type: DataSnapshot in activity_num.children) {
                        when(unit_type.key ){
                            "contents" -> {
                                contents.content = unit_type.child("content").value.toString()
                                contents.pic = unit_type.child("pic").value.toString()
                            }
                            "info" -> {
                                info.activity_id = unit_type.child("activity_id").value.toString().toInt()
                                info.subject = unit_type.child("subject").value.toString()
                                info.time = unit_type.child("time").value.toString()
                                info.writer = unit_type.child("writer").value.toString()
                            }
                            "participates" -> {
                                participates.id = unit_type.child("id").value.toString()
                                participates.review = unit_type.child("review").value.toString()
                            }
                            "tag" -> {
                                tag.concept = unit_type.child("concept").value.toString()
                                tag.field = unit_type.child("field").value.toString()
                                tag.pattern = unit_type.child("pattern").value.toString()
                                tag.peoplenum = unit_type.child("peoplenum").value.toString().toInt()
                            }
                        }
//                        for (values: DataSnapshot in unit_type.children) {
//                            str += " child : " + activity_num.key.toString() +
//                                    " value name : " + values.key.toString() +
//                                    " value : " + values.value.toString() + "\n"
//                            Log.d(TAG, "Value is: " + str);
//                        }
                    }
                    Log.d(TAG, "Value is: " +tag.concept);
                    data_activity = Data_Activity(contents, info, participates, tag )
                    data.add(data_activity)
                }
                textView2.text = data[0].tag.concept
            }
        })
    }

    fun insertData(activity_id : String){
        Log.d(TAG, "insertData()");
        rdb.child("activity").child(activity_id).child("contents").setValue("제발올라가주렴")
            .addOnSuccessListener {
                Log.d(TAG, "저장성공 ");
            }
            .addOnFailureListener{
                Log.d(TAG, "저장실패 ");
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
