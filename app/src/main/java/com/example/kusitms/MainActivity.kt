package com.example.kusitms

import android.annotation.SuppressLint
import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_tab.view.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*


class MainActivity : AppCompatActivity() {
    val textArray = arrayListOf<String>("홈", "장소","사람", "액티비티", "MY")
    val iconArray = arrayListOf<Int>(R.drawable.icon_home, R.drawable.icon_place,
        R.drawable.icon_person, R.drawable.icon_activity, R.drawable.icon_mypage )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        init()
    }

    @SuppressLint("WrongConstant", "ResourceAsColor")
    fun init(){
        val viewArray =
            arrayListOf(layoutInflater.inflate(R.layout.custom_tab, null),
                layoutInflater.inflate(R.layout.custom_tab, null),
                layoutInflater.inflate(R.layout.custom_tab, null),
                layoutInflater.inflate(R.layout.custom_tab, null),
                layoutInflater.inflate(R.layout.custom_tab, null))

        viewPager.adapter = MyFragStateAdapter(this)
        viewPager.isUserInputEnabled = false
//        setupTabIcons(viewPager) //훔..

        TabLayoutMediator(tabLayout, viewPager){
                tab: TabLayout.Tab, position: Int ->
            viewArray[position].txt_tab.setText(textArray[position])
            viewArray[position].img_tab.setImageResource(iconArray[position])
//            viewArray[position].setBackgroundColor(R.color.white)
            tab.customView = viewArray[position]
        }.attach()

//        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
//        supportActionBar?.setCustomView(R.layout.actionbar)
    }
}