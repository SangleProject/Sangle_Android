package org.three.minutes.badge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.badge.data.BadgeListData
import org.three.minutes.badge.viewmodel.BadgeViewModel
import org.three.minutes.databinding.ActivityBadgeBinding
import org.three.minutes.singleton.StatusObject

class BadgeActivity : AppCompatActivity() {
    private val mViewModel : BadgeViewModel by viewModels()
    private val mBinding : ActivityBadgeBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_badge)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@BadgeActivity
            viewModel = mViewModel
        }

        StatusObject.setStatusBar(this)

        settingToolbar()
        settingRcv()

    }
    // 뱃지 리스트 리사이클러뷰 세팅
    private fun settingRcv() {
        mViewModel.badgeList = mutableListOf(
            BadgeListData(title = "시작이 반이다", isOpen = 0),
            BadgeListData(title = "시작이 반이다", isOpen = 0),
            BadgeListData(title = "시작이 반이다", isOpen = 0),
            BadgeListData(title = "시작이 반이다", isOpen = 0),
            BadgeListData(title = "시작이 반이다", isOpen = 0),
            BadgeListData(title = "시작이 반이다", isOpen = 0),
            BadgeListData(title = "시작이 반이다", isOpen = 0),
            BadgeListData(title = "시작이 반이다", isOpen = 1),
            BadgeListData(title = "시작이 반이다", isOpen = 1),
            BadgeListData(title = "시작이 반이다", isOpen = 1),
            )
    }

    private fun settingToolbar() {
        setSupportActionBar(mBinding.badgeToolbar)

        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic__back_black)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            // back Icon Click Listener
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}