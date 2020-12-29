package org.three.minutes.home.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.home_navigation.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.badge.ui.BadgeActivity
import org.three.minutes.databinding.ActivityHomeBinding
import org.three.minutes.home.adapter.HomePageAdapter
import org.three.minutes.home.viewmodel.HomeViewModel
import org.three.minutes.mypage.ui.MyPageActivity
import org.three.minutes.profile.ui.ProfileChangeActivity
import org.three.minutes.util.customChangeListener
import org.three.minutes.word.WordActivity
import kotlin.coroutines.CoroutineContext

class HomeActiviy : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mViewModel : HomeViewModel by viewModels()

    private val mBinding : ActivityHomeBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_home)
    }

    private val PROFILE_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        mBinding.apply {
            lifecycleOwner = this@HomeActiviy
            viewModel = mViewModel
        }

        // 기기에 저장된 token값 가져오기
        launch {
            ThreeApplication.getInstance().getDataStore().token.collect {
                mViewModel.token = it
            }
        }

        //Home_Info Api
        mViewModel.setInfo()

        setSupportActionBar(home_toolbar)
        supportActionBar?.apply {
            setDisplayShowCustomEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        settingDrawer()



        mBinding.homeToolbar.setNavigationOnClickListener {
            Log.d("OpenDrawer", "Open")
            if (!home_drawer.isDrawerOpen(GravityCompat.START)) {
                home_drawer.openDrawer(GravityCompat.START)
            }
        }


        initViewPager()

    }

    private fun settingDrawer() {
        // 드로어 레이아웃 슬라이드 잠금 여부 설정
        mBinding.homeDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        mBinding.homeDrawer.ic_navi_right.setOnClickListener {
            val intent = Intent(this, ProfileChangeActivity::class.java)
            startActivityForResult(intent,PROFILE_CODE)
        }
        mBinding.homeDrawer.close_navi_img.setOnClickListener {
            mBinding.homeDrawer.close()
        }
        mBinding.homeDrawer.navi_writing_txt.setOnClickListener {
            val intent = Intent(this, WordActivity::class.java)
            startActivity(intent)
        }
        mBinding.homeDrawer.navi_draw_txt.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        // 뱃지 탭 클릭 시 뱃지 화면으로 이동
        mBinding.homeDrawer.navi_badge.setOnClickListener {
            val intent = Intent(this, BadgeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViewPager() {
        mBinding.homePage.adapter = HomePageAdapter(supportFragmentManager)
        //가운데가 기준
        mBinding.homeBottomNavi.menu.getItem(1).isChecked = true
        mBinding.homePage.currentItem = 1

        //뷰페이저 슬라이드 시 바텀네비 아이콘 상태 변경
        mBinding.homePage.customChangeListener {
            mBinding.homeBottomNavi.menu.getItem(it).isChecked = true
        }

        //아이콘 안보여서 속성 설정
        mBinding.homeBottomNavi.itemIconTintList = null

        mBinding.homeBottomNavi.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_feed -> home_page.currentItem = 0
                R.id.menu_main -> home_page.currentItem = 1
                R.id.menu_calender -> home_page.currentItem = 2
            }
            true
        }
    }

    override fun onBackPressed() {
//        if (mBinding.homeDrawer.isDrawerOpen(GravityCompat.START)) {
//            mBinding.homeDrawer.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
        super.onBackPressed()
        ActivityCompat.finishAffinity(this)
//        exitProcess(0)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_hold, R.anim.slide_out_right)

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PROFILE_CODE){
            if (resultCode == RESULT_OK){
                Toast.makeText(this,"Profile Change Success",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Profile Change Cancel",Toast.LENGTH_SHORT).show()
            }
        }
    }
}