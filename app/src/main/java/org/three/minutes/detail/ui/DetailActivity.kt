package org.three.minutes.detail.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.*
import min.dev.singleclick.mingSingleClickListener
import org.three.minutes.R
import org.three.minutes.databinding.ActivityDetailBinding
import org.three.minutes.databinding.BottomSheetReportBinding
import org.three.minutes.detail.viewmodel.DetailOtherViewModel
import org.three.minutes.profile.ui.OtherProfileActivity

class DetailActivity : AppCompatActivity() {
    private val mBinding: ActivityDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }
    private val mViewModel: DetailOtherViewModel by viewModels()

    // 신고하기 바텀 시트
    private val reportBottomSheet by lazy {
        ReportBottomDialog()
    }

    // 신고하기 바텀시트 바인딩
    private val reportBottomBinding: BottomSheetReportBinding by lazy {
        DataBindingUtil.inflate<BottomSheetReportBinding>(
            layoutInflater,
            R.layout.bottom_sheet_report,
            null,
            false
        ).apply {
            lifecycleOwner = this@DetailActivity
            viewModel = mViewModel
        }
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