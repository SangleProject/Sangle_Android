package org.three.minutes.login.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivityOnBoardingBinding
import org.three.minutes.login.adapter.OnBoardingViewPagerAdapter
import org.three.minutes.singleton.StatusObject


@Suppress("DEPRECATION")
class OnBoardingActivity : AppCompatActivity() {
    private val mBinding: ActivityOnBoardingBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        window.apply {
//            statusBarColor = Color.TRANSPARENT
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                setDecorFitsSystemWindows(false)
//                val controller = insetsController
//                if (controller != null) {
//                    controller.hide(WindowInsets.Type.statusBars())
//                    controller.systemBarsBehavior =
//                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//                }
//            } else {
//                // systemUiVisibility의 deprecated 이슈로 인해서 insetsController를 사용하려 했지만
//                // Android 11 부터 적용가능하기 때문에 여의치 않게 DEPRECATIONS 어노테이션 사용
//                // 추후 내용을 찾게 된다면 변경 필요
//                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
//                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            }
//        }

        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


        initViewPager()
        mBinding.onBoardingSkip.setOnClickListener {
            setDataStoreOnBoarding()
        }

        mBinding.onBoardingSkip
            .setTextColor(
                ContextCompat.getColor(
                    this@OnBoardingActivity,
                    R.color.white
                )
            )

    }

    fun setDataStoreOnBoarding() {
        CoroutineScope(Dispatchers.Main).launch {
            launch {
                ThreeApplication.getInstance().getDataStore().setOnBoarding(false)
                Log.e("OnBoardingActivity", "Ok")
            }.join()
            callMainActivity()
        }
    }

    private fun callMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initViewPager() {
        mBinding.onBoardingViewpager.apply {
            adapter = OnBoardingViewPagerAdapter(supportFragmentManager)
            offscreenPageLimit = 2
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    // 현재 인덱스에 따라 skip 색상 변경
                    when (position) {
                        0 -> {
                            mBinding.onBoardingSkip
                                .setTextColor(
                                    ContextCompat.getColor(
                                        this@OnBoardingActivity,
                                        R.color.white
                                    )
                                )
                            if (mBinding.onBoardingSkip.visibility != View.VISIBLE) {
                                mBinding.onBoardingSkip.visibility = View.VISIBLE
                            }
                        }
                        5 -> {

                            mBinding.onBoardingSkip.visibility = View.GONE
                        }
                        else -> {

                            mBinding.onBoardingSkip
                                .setTextColor(
                                    ContextCompat.getColor(
                                        this@OnBoardingActivity,
                                        R.color.main_blue
                                    )
                                )
                            if (mBinding.onBoardingSkip.visibility != View.VISIBLE) {
                                mBinding.onBoardingSkip.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            })
        }
    }
}