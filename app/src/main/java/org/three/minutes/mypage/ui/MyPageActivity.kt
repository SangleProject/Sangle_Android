package org.three.minutes.mypage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityMyPageBinding
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
    }
}