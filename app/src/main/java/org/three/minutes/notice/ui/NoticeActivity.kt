package org.three.minutes.notice.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityNoticeBinding

class NoticeActivity : AppCompatActivity() {
    private val mBinding : ActivityNoticeBinding by lazy{
        DataBindingUtil.setContentView(this, R.layout.activity_notice)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@NoticeActivity
        }
    }
}