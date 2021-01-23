package org.three.minutes.profile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityOtherProfileBinding

class OtherProfileActivity : AppCompatActivity() {
    private val mBinding : ActivityOtherProfileBinding by lazy{
        DataBindingUtil.setContentView(this, R.layout.activity_other_profile)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@OtherProfileActivity
        }
    }
}