package org.three.minutes.home.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_home.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityHomeBinding
import org.three.minutes.home.adapter.HomePageAdapter
import org.three.minutes.util.customChangeListener

class HomeActiviy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityHomeBinding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        binding.apply {
            lifecycleOwner = this@HomeActiviy
        }

        setSupportActionBar(home_toolbar)
        supportActionBar?.apply {
            setDisplayShowCustomEnabled(true)
            setDisplayShowTitleEnabled(false)
        }




        initViewPager()

    }

    private fun initViewPager() {
        home_page.adapter = HomePageAdapter(supportFragmentManager)
        //가운데가 기준
        home_bottom_navi.menu.getItem(1).isChecked = true
        home_page.currentItem = 1

        //뷰페이저 슬라이드 시 바텀네비 아이콘 상태 변경
        home_page.customChangeListener {
            home_bottom_navi.menu.getItem(it).isChecked = true
        }

        //아이콘 안보여서 속성 설정
        home_bottom_navi.itemIconTintList = null

        home_bottom_navi.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_feed -> home_page.currentItem = 0
                R.id.menu_main-> home_page.currentItem = 1
                R.id.menu_calender -> home_page.currentItem = 2
            }
            true
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_hold, R.anim.slide_out_right)

    }
}