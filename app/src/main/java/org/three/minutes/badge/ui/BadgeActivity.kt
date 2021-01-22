package org.three.minutes.badge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.badge_opened_popup.*
import org.three.minutes.R
import org.three.minutes.badge.data.BadgeListData
import org.three.minutes.badge.viewmodel.BadgeViewModel
import org.three.minutes.databinding.ActivityBadgeBinding
import org.three.minutes.singleton.PopUpObject
import org.three.minutes.singleton.StatusObject

class BadgeActivity : AppCompatActivity() {
    private val mViewModel : BadgeViewModel by viewModels()
    private val mBinding : ActivityBadgeBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_badge)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel.getToken.observe(this,{
            mViewModel.token = it
        })

        mBinding.apply {
            lifecycleOwner = this@BadgeActivity
            viewModel = mViewModel
        }
        mViewModel.callBadgeList()

        StatusObject.setStatusBar(this)

        settingToolbar()

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