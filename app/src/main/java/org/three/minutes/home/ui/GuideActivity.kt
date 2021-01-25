package org.three.minutes.home.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityGuideBinding

class GuideActivity : AppCompatActivity() {
    private val mBinding : ActivityGuideBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.activity_guide)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@GuideActivity
        }

        mBinding.drawToolbar.setNavigationOnClickListener {
            finish()
        }

    }
}