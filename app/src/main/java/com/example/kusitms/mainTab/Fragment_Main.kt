package com.example.kusitms.mainTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide.init
import com.example.kusitms.R
import com.example.kusitms.activityTab.Adapter_Activity
import com.example.kusitms.activityTab.Data_Activity
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class Fragment_Main : Fragment() {
    private var root: View? = null
    lateinit var activityAdapter: Adapter_Main
    lateinit var personAdapter: Adapter_Main
    lateinit var placeAdapter: Adapter_Main


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        initActivityAdapter()
        initPersonAdapter()
        initPlaceAdapter()
        init()
    }

    override fun onStop() {
        super.onStop()
        activityAdapter.stopListening()
        personAdapter.stopListening()
        placeAdapter.stopListening()
    }

    fun init() {
        root!!.activityRecyclerView.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )

        root!!.personRecyclerView.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )

        root!!.placeRecyclerView.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    fun initActivityAdapter() {
        //처음 들어갈 때 그냥 첫번째 라디오버튼 눌린거로 보여주기 위해서 강연로 쿼리,,

        val activityquery = FirebaseDatabase.getInstance().reference
            .child("activity").limitToLast(50)

        val activityoption = FirebaseRecyclerOptions.Builder<Data_home>()
            .setQuery(activityquery,
                SnapshotParser { snapshot ->
                    Data_home(
                        snapshot.child("activity_subject").value.toString(),
                        snapshot.child("activity_pic_url").value.toString()
                    )
                })
            .build()

        activityAdapter = Adapter_Main(activityoption)
        activityRecyclerView.adapter = activityAdapter
        activityAdapter.startListening()
    }

    fun initPlaceAdapter() {
        //처음 들어갈 때 그냥 첫번째 라디오버튼 눌린거로 보여주기 위해서 강연로 쿼리,,

        val placequery = FirebaseDatabase.getInstance().reference
            .child("place").limitToLast(50)

        val placeoption = FirebaseRecyclerOptions.Builder<Data_home>()
            .setQuery(placequery,
                SnapshotParser { snapshot ->
                    Data_home(
                        snapshot.child("place_subject").value.toString(),
                        snapshot.child("place_photo").value.toString()
                    )
                })
            .build()

        placeAdapter = Adapter_Main(placeoption)
        placeRecyclerView.adapter = placeAdapter
        placeAdapter.startListening()
    }

    fun initPersonAdapter() {
        //처음 들어갈 때 그냥 첫번째 라디오버튼 눌린거로 보여주기 위해서 강연로 쿼리,,

        val personquery = FirebaseDatabase.getInstance().reference
            .child("person").limitToLast(50)

        val personoption = FirebaseRecyclerOptions.Builder<Data_home>()
            .setQuery(personquery,
                SnapshotParser { snapshot ->
                    Data_home(
                        snapshot.child("person_writer").value.toString(),
                        snapshot.child("place_photo").value.toString()
                        //아직 사람은 디비에 이미지 안넣은듯
                    )
                })
            .build()

        personAdapter = Adapter_Main(personoption)
        personRecyclerView.adapter = personAdapter
        personAdapter.startListening()
    }



}
