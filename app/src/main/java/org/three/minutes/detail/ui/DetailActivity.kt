package org.three.minutes.detail.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.*
import min.dev.singleclick.mingSingleClickListener
import org.three.minutes.R
import org.three.minutes.databinding.ActivityDetailBinding
import org.three.minutes.detail.viewmodel.DetailOtherViewModel
import org.three.minutes.profile.ui.OtherProfileActivity

class DetailActivity : AppCompatActivity() {
    private val mBinding: ActivityDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }
    private val mViewModel: DetailOtherViewModel by viewModels()

    // 신고하기 바텀 시트
    private val reportBottomSheet by lazy {
        ReportBottomDialog(object : ReportBottomDialog.ReportClickListener {
            override fun onClickOk() {
                reportCheckDialog.show()
            }
        })
    }

    // 신고하기 한번 더 체크하는 다이얼로그
    private val reportCheckDialog by lazy {
        ReportDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@DetailActivity
        }

        setToolbarIcon()

        mViewModel.getToken.observe(this, {
            mViewModel.token = it
        })
        getIntentData()
        mViewModel.callOtherDetailData()


        mBinding.viewmodel = mViewModel

        setTextSizeButton()
        setLikeListener()
        setScrapListener()

        mBinding.detailContentsTxt.movementMethod = ScrollingMovementMethod()

        // 신고하기 체크 다이얼로그 리스너 장착
        reportCheckDialog.setClickListener(object : ReportDialog.PopUpClickListener {
            override fun setOnOk(dialog: Dialog) {
                dialog.dismiss()
            }
        })

        mBinding.profileId.setOnClickListener {
            val intent = Intent(this, OtherProfileActivity::class.java)
            intent.putExtra("nickName", mViewModel.detailData.value?.nickName ?: "")
            startActivity(intent)
        }

        mBinding.imgReport.mingSingleClickListener {
            reportBottomSheet.show(supportFragmentManager, "report_dialog")
        }

    }

    private fun setScrapListener() {
        mBinding.detailPutBtn.setOnCheckedChangeListener { _, isChecked ->
            // 스크랩 하기
            if (isChecked) {
                if (mViewModel.scrapFirst) {
                    mViewModel.scrapFirst = false
                } else {
                    mViewModel.callScrap(this)
                }
            } else {// 스크랩 해제
                mViewModel.callUnScrap(this)
            }
        }
    }

    private fun setLikeListener() {
        mBinding.detailLikeTxt.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (mViewModel.likeFirst) {
                    mViewModel.likeFirst = false
                } else {
                    mViewModel.callLike(this)
                }
            } else {
                mViewModel.callUnLike(this)
            }
        }
    }

    private fun setTextSizeButton() {
        mBinding.detailFontSizeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.size_from_minus_to_plus -> {
                    mBinding.detailContentsTxt.textSize = 18f
                }
                R.id.size_from_plus_to_minus -> {
                    mBinding.detailContentsTxt.textSize = 16f
                }
            }
        }
    }

    private fun getIntentData() {

        val postIdx = intent.getIntExtra("postIdx", -1)
        mViewModel.postIdx = postIdx
    }

    private fun setToolbarIcon() {
        mBinding.detailToolbar.setNavigationOnClickListener {
            finish()
        }
    }

}