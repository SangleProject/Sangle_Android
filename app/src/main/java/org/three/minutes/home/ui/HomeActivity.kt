package org.three.minutes.home.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.home_navigation.view.*
import kotlinx.coroutines.*
import org.three.minutes.LogOutPopUp
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.badge.ui.BadgeActivity
import org.three.minutes.badge.ui.OpenedBadgePopup
import org.three.minutes.databinding.ActivityHomeBinding
import org.three.minutes.home.adapter.HomePageAdapter
import org.three.minutes.home.viewmodel.HomeUseCase
import org.three.minutes.home.viewmodel.HomeViewModel
import org.three.minutes.home.viewmodel.HomeViewModelFactory
import org.three.minutes.login.ui.MainActivity
import org.three.minutes.mypage.ui.MyPageActivity
import org.three.minutes.notice.ui.NoticeActivity
import org.three.minutes.preferences.ui.PreferencesActivity
import org.three.minutes.profile.ui.ProfileChangeActivity
import org.three.minutes.singleton.GoogleLoginObject
import org.three.minutes.util.customChangeListener
import org.three.minutes.util.showToast
import org.three.minutes.word.ui.WordActivity
import org.three.minutes.writing.data.BadgeData
import kotlin.coroutines.CoroutineContext

class HomeActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var mViewModel: HomeViewModel

    private val mBinding: ActivityHomeBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_home)
    }

    private val PROFILE_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Home", "HomeActivity onCreate()")

        job = Job()

        mViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(ThreeApplication.getInstance(), HomeUseCase())
        )
            .get(HomeViewModel::class.java)

        mBinding.apply {
            lifecycleOwner = this@HomeActivity
            viewModel = mViewModel
        }

        setObserve()
        setSwipeRefresh()

        // 기기에 저장된 token값 가져오기
        ThreeApplication.getInstance().getDataStore().token.asLiveData()
            .observe(this@HomeActivity, {
                mViewModel.token = it
            })

        //Home_Info Api
        mViewModel.setInfo()
        mViewModel.callFameData()
        mViewModel.setInitialCalendarData(0)

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

    private fun setSwipeRefresh() {
        mBinding.swipe.setColorSchemeColors(ContextCompat.getColor(this,R.color.main_blue))
        mBinding.swipe.setOnRefreshListener {
            val intent = Intent(this,HomeActivity::class.java)
            overridePendingTransition(0,0)
            startActivity(intent)
            finish()

            mBinding.swipe.isRefreshing = false
        }
    }

    private fun setObserve() {
        mViewModel.badgeList.observe(this,{
            if (it.isNotEmpty()){
                showPopUp(it)
            }
        })
    }

    private fun showPopUp(badge: MutableList<BadgeData>?) {
        if (badge!!.isEmpty()){
            return
        }
        else{
            val popUp = OpenedBadgePopup(this,badge[0])
            badge.removeAt(0)
            popUp.setListener(object : OpenedBadgePopup.OnCloseListener{
                override fun closePopUp(v: Dialog) {
                    v.dismiss()
                    showPopUp(badge)
                }
            })
            popUp.show()
        }
    }

    private fun settingDrawer() {
        // 드로어 레이아웃 슬라이드 잠금 여부 설정
        mBinding.homeDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        mBinding.homeDrawer.layout_profile.setOnClickListener {
            val intent = Intent(this, ProfileChangeActivity::class.java)
            startActivityForResult(intent, PROFILE_CODE)
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

        // 설정 아이콘 클릭 시 설정 화면으로 이동
        mBinding.homeDrawer.ic_setting.setOnClickListener {
            val intent = Intent(this, PreferencesActivity::class.java)
            startActivity(intent)
        }

        // 이용 안내 클릭 시 이용 안내 화면으로 이동
       mBinding.homeDrawer.navi_info_txt.setOnClickListener {
            val intent = Intent(this, GuideActivity::class.java)
            startActivity(intent)
        }

        // 공지사항 클릭 시 공지사항 뷰로 이동
        mBinding.homeDrawer.navi_notice_txt.setOnClickListener {
            val intent = Intent(this,NoticeActivity::class.java)
            startActivity(intent)
        }

        //이메일 문의하기 클릭 시 이메일 창 띄우기
        mBinding.homeDrawer.navi_mail_txt.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "plain/text"
            val address = arrayOf("brain.malang@gmail.com")
            intent.apply {
                putExtra(Intent.EXTRA_EMAIL, address)
                putExtra(Intent.EXTRA_SUBJECT, "생글 문의")
                putExtra(Intent.EXTRA_TEXT, getString(R.string.mail_contents))
            }
            startActivityForResult(intent,3000)
        }
    }


    private fun initViewPager() {
        mBinding.homePage.adapter = HomePageAdapter(supportFragmentManager)
        mBinding.homePage.offscreenPageLimit = 2
        //가운데가 기준
        mBinding.homeBottomNavi.menu.getItem(1).isChecked = true
        mBinding.homePage.currentItem = 1

        // 스와이프 리프레시와 터치 이벤트 겹치는 현상 수정
        mBinding.homePage.customChangeListener(
            pageSelect ={
                mBinding.homeBottomNavi.menu.getItem(it).isChecked = true
            },
            pageScrollState ={
                isEnableSwipeRefresh(it == ViewPager.SCROLL_STATE_IDLE)
            }

        )



        //아이콘 안보여서 속성 설정
        mBinding.homeBottomNavi.itemIconTintList = null

        //뷰페이저 슬라이드 시 바텀네비 아이콘 상태 변경
        mBinding.homeBottomNavi.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_feed -> home_page.currentItem = 0
                R.id.menu_main -> home_page.currentItem = 1
                R.id.menu_calender -> home_page.currentItem = 2
            }
            true
        }
    }

    fun isEnableSwipeRefresh(isEnable : Boolean){
        mBinding.swipe.isEnabled = isEnable
    }

    override fun onBackPressed() {
        if (mBinding.homeDrawer.isDrawerOpen(GravityCompat.START)) {
            mBinding.homeDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            ActivityCompat.finishAffinity(this)
        }
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
        when(requestCode) {
            PROFILE_CODE -> {
                if (resultCode == RESULT_OK) {
                    mViewModel.setInfo()
                    Toast.makeText(this, "프로필을 저장했어요!", Toast.LENGTH_SHORT).show()
                }

            }
            3000 -> {
                showToast("여러분의 소중한 의견이 잘 전달되었어요 :)")
            }
        }
    }
}