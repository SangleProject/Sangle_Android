package org.three.minutes.badge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityBadgeBinding
import org.three.minutes.singleton.StatusObject

class BadgeActivity : AppCompatActivity() {
    private val mBinding : ActivityBadgeBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_badge)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@BadgeActivity
        }

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