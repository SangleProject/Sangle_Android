package org.three.minutes.mypage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityMyPageBinding
import org.three.minutes.mypage.adapter.MyViewPagerAdapter
import org.three.minutes.mypage.viewmodel.MyPageViewModel

class MyPageActivity : AppCompatActivity() {

    private val mBinding : ActivityMyPageBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_my_page)
    }

    private val mViewModel : MyPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@MyPageActivity
            viewModel = mViewModel
        }

        setMyViewPager()
        setMyTabLayout()
    }

    private fun setMyTabLayout() {
        mBinding.myWritingTabLayout.setupWithViewPager(mBinding.myWritingViewpager)
        mBinding.myWritingTabLayout.apply {
            getTabAt(0)?.text = "내가 쓴 글"
            getTabAt(1)?.text = "담은 글"
        }
    }

    private fun setMyViewPager() {
        val adapter = MyViewPagerAdapter(supportFragmentManager)
        mBinding.myWritingViewpager.adapter = adapter
    }


}