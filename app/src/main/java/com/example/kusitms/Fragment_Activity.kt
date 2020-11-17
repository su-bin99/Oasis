package com.example.kusitms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_person.view.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment_Activity : Fragment() {
    private var root: View? = null
    var data:ArrayList<Data_Activity> = ArrayList<Data_Activity>()

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
        root!!.recyclerView.adapter = Adapter_Activity(data)
    }
}
