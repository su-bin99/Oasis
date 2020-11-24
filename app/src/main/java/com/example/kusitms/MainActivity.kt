package com.example.kusitms

import android.annotation.SuppressLint
import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val textArray = arrayListOf<String>("메인", "장소","사람", "액티비티", "마이페이지")
    val iconArray = arrayListOf<Int>(R.drawable.icon_gyoyang, R.drawable.icon_major,
        R.drawable.icon_map, R.drawable.icon_programmer, R.drawable.icon_programmer )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        init()
    }

    @SuppressLint("WrongConstant")
    fun init(){
        viewPager.adapter = MyFragStateAdapter(this)
        viewPager.isUserInputEnabled = false

        TabLayoutMediator(tabLayout, viewPager){
                tab: TabLayout.Tab, position: Int ->
            tab.text = textArray[position]
            tab.setIcon(iconArray[position])
        }.attach()
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.actionbar)
    }

}