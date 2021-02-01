package org.three.minutes.notice.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityNoticeBinding
import org.three.minutes.notice.data.ResponseNoticeData
import org.three.minutes.notice.viewmodel.NoticeViewModel

class NoticeActivity : AppCompatActivity() {
    private val mBinding: ActivityNoticeBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_notice)
    }
    private val mViewModel: NoticeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel._token.observe(this, {
            mViewModel.token = it
        })


        mBinding.apply {
            lifecycleOwner = this@NoticeActivity
            viewModel = mViewModel
        }

        setToolBarListener()
        setNoticeListItem()
    }

    private fun setNoticeListItem() {
        mViewModel.callGetNotice()
    }

    private fun setToolBarListener() {
        mBinding.drawToolbar.setNavigationOnClickListener {
            finish()
        }
    }
}