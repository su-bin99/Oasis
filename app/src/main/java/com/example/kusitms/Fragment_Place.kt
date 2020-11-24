package com.example.kusitms

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.*
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment_Place : Fragment() {
    private var root: View? = null

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

        writeplace.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v : View?){
                //fragment에서 activity로 이동
//                val intent = Intent(context, WriteActivity_Place::class.java)
//                startActivity(intent)
            }
        })
    }
}