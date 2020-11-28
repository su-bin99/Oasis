package com.example.kusitms.placeTab

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton

import com.example.kusitms.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
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
    var radio_checkedid_before = 0

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
        var lectureRadio: RadioButton = root!!.findViewById(R.id.pl_radio_shareOffice)
        lectureRadio.isChecked = true
        radio_checkedid_before = R.id.pl_radio_shareOffice

        plWriteButton.setOnClickListener{
            val intent = Intent(context, WriteActivity_Place::class.java)
            startActivity(intent)
        }

        pl_radioGroup.setOnCheckedChangeListener { group, checkedId ->
            var thisCheckedBtn: RadioButton = root!!.findViewById(checkedId)
            if(radio_checkedid_before != checkedId){
                var beforeCheckedBtn: RadioButton = root!!.findViewById(radio_checkedid_before)
                beforeCheckedBtn.setTextColor(R.color.darkGray)
            }
            when (checkedId) {
                R.id.pl_radio_shareOffice -> {
                    findQueryAdapter("공유오피스")
                    radio_checkedid_before = R.id.pl_radio_shareOffice
                }
                R.id.pl_radio_personalOffice -> {
                    findQueryAdapter("개인오피스")
                    radio_checkedid_before = R.id.pl_radio_personalOffice
                }
                R.id.pl_radio_studyRoom -> {
                    findQueryAdapter("스터디룸")
                    radio_checkedid_before = R.id.pl_radio_studyRoom
                }
                R.id.pl_radio_cafe -> {
                    findQueryAdapter("카페")
                    radio_checkedid_before = R.id.pl_radio_cafe
                }
                R.id.pl_radio_etc -> {
                    findQueryAdapter("기타")
                    radio_checkedid_before = R.id.pl_radio_etc
                }
            }
        }
    }

    fun initAdapter(){
        //처음 들어갈 때 그냥 첫번째 라디오버튼 눌린거로 보여주기 위해서 공유오피스로 쿼리문... 파이팅

//        val query = FirebaseDatabase.getInstance().reference
//            .child("place").limitToLast(50)

        val query = FirebaseDatabase.getInstance().reference
            .child("place").orderByChild("place_tag/place_type").equalTo("공유오피스")

        val option = FirebaseRecyclerOptions.Builder<Data_Place>()
            .setQuery(query) { snapshot ->
                Data_Place(
                    snapshot.child("place_content").value.toString(),
                    snapshot.child("place_photo").value.toString(),
                    snapshot.child("place_price").value.toString(),
                    snapshot.child("reserve_person").value.toString(),
                    snapshot.child("place_reserve").child("place_review").value.toString(),
                    snapshot.child("place_subject").value.toString(),
                    snapshot.child("place_tag").child("place_concept").value.toString(),
                    snapshot.child("place_tag").child("place_maxnum").value.toString(),
                    snapshot.child("place_tag").child("place_type").value.toString(),
                    snapshot.child("place_time").value.toString(),
                    snapshot.child("place_writer").value.toString(),
                    snapshot.child("place_uid").value.toString()
                )
            }
            .build()

        adapter = Adapter_Place(option)
        recyclerView.adapter = adapter
        adapter.startListening()
    }

    @SuppressLint("RestrictedApi")
    fun findQueryAdapter(place_type: String) {
        if (adapter != null)
            adapter.stopListening()
        val query = FirebaseDatabase.getInstance().reference
            .child("place").orderByChild("place_tag/place_type").equalTo(place_type)

        val option = FirebaseRecyclerOptions.Builder<Data_Place>()
            .setQuery(query,
                SnapshotParser { snapshot ->
                    Data_Place(
                        snapshot.child("place_content").value.toString(),
                        snapshot.child("place_photo").value.toString(),
                        snapshot.child("place_price").value.toString(),
                        snapshot.child("reserve_person").value.toString(),
                        snapshot.child("place_reserve/place_review").value.toString(),
                        snapshot.child("place_subject").value.toString(),
                        snapshot.child("place_tag/place_concept").value.toString(),
                        snapshot.child("place_tag/place_maxnum").value.toString(),
                        snapshot.child("place_tag/place_type").value.toString(),
                        snapshot.child("place_time").value.toString(),
                        snapshot.child("place_writer").value.toString(),
                        snapshot.child("place_uid").value.toString()
                    )
                })
            .build()

        println(place_type + "갯수 : " + option.snapshots.size)
        adapter = Adapter_Place(option)
        recyclerView.adapter = adapter
        adapter.startListening()

    }

}