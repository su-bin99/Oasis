package com.example.kusitms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_mypage.*

/**
 * A simple [Fragment] subclass.
 */
class Fragment_MyPage : Fragment() {
    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_mypage, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    fun init() {
        Glide.with(this).load(R.drawable.membershipicon1).circleCrop().into(membershipIcon);
        Glide.with(this).load(R.drawable.user).circleCrop().into(mypage_personalimg);

    }

}
