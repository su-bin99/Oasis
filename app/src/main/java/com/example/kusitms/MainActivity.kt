package com.example.kusitms

import android.annotation.SuppressLint
import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val textArray = arrayListOf<String>("추천", "공고","커뮤니티", "마이페이지")
    val iconArray = arrayListOf<Int>(R.drawable.icon_gyoyang, R.drawable.icon_major,
        R.drawable.icon_map, R.drawable.icon_programmer )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        init()
    }

    @SuppressLint("WrongConstant")
    fun init(){
        viewPager.adapter = MyFragStateAdapter(this)
        TabLayoutMediator(tabLayout, viewPager){
                tab: TabLayout.Tab, position: Int ->
            tab.text = textArray[position]
            tab.setIcon(iconArray[position])
        }.attach()
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.actionbar)
    }

}
