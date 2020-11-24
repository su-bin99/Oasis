package com.example.kusitms

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kusitms.activityTab.Fragment_Activity
import com.example.kusitms.mainTab.Fragment_Main
import com.example.kusitms.mypageTab.Fragment_MyPage
import com.example.kusitms.personTab.Fragment_Person
import com.example.kusitms.placeTab.Fragment_Place

class MyFragStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> Fragment_Main()
            1-> Fragment_Place()
            2-> Fragment_Person()
            3-> Fragment_Activity()
            4-> Fragment_MyPage()
            else-> Fragment_Main()
        }
    }
}