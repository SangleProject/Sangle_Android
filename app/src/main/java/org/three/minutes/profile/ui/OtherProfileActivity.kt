package org.three.minutes.profile.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.modal_bottom_sheet.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivityOtherProfileBinding
import org.three.minutes.profile.viewmodel.OtherProfileViewModel
import org.three.minutes.util.CustomDialog
import org.three.minutes.util.showToast

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OtherProfileActivity : AppCompatActivity() {
    private val mBinding: ActivityOtherProfileBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_other_profile)
    }
    private val mViewModel: OtherProfileViewModel by viewModels()

    private val bottomSheet: BottomSheetDialog by lazy {
        BottomSheetDialog(mBinding.root.context)
    }

    private val blockUserDialog: CustomDialog by lazy {
        CustomDialog(
            this,
            getString(
                R.string.are_you_really_block_user
            ),
            getString(
                R.string.are_you_really_block_user_contents
            ),
            "네, 차단할게요.",
            "취소할게요.",
            R.drawable.illust_popup02
        ).apply {
            setDialogClickListener(object : CustomDialog.ClickListener {
                override fun setOnOk(dialog: Dialog) {}

                override fun setOnCancel(dialog: Dialog) {
                    mViewModel.postUserBlock()
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel.getToken.observe(this, {
            mViewModel.token = it
        })
        initIntent()
        setObserve()

        mBinding.apply {
            lifecycleOwner = this@OtherProfileActivity
            viewModel = mViewModel
        }

        setBottomSheet()

        mBinding.profileFilterTxt.setOnClickListener {
            bottomSheet.show()
        }

        mBinding.drawToolbar.setNavigationOnClickListener {
            finish()
        }

        mBinding.btnUserBlock.setOnClickListener {
            blockUserDialog.show()
        }
    }

    private fun initIntent() {
        mViewModel.userName.value = intent.getStringExtra("nickName")
    }

    private fun setObserve() {
        mViewModel.filter.observe(this, {
            if (it == "최신순") {
                mViewModel.setRcvRecent()
            } else {
                mViewModel.setRcvPopular()
            }
        })

        mViewModel.userName.observe(this, {
            if (it.isNotBlank()) {
                callApi()
            }
        })

        mViewModel.diffInfo.observe(this, {
            mBinding.profileNickname.text = it.nickName
            mBinding.profileIntro.text = it.info
        })

        mViewModel.postBlockUserSuccess.observe(this) {
            it?.let { isSuccess ->
                if (isSuccess) {
                    showToast("성공적으로 차단되었습니다.")
                } else {
                    showToast("이미 차단되어있는 유저입니다.")
                }
            }
        }

        mViewModel.postBlockUserErrorCode.observe(this) {
            it?.let { errorCode ->
                showToast("오류가 발생했어요. 잠시 후에 다시 시도해 주세요. error : $errorCode")
            }
        }
    }

    private fun callApi() {
        mViewModel.callDiffInfo()
        mViewModel.callDiffFeedPopular()
        mViewModel.callDiffFeedRecent()
    }

    private fun setBottomSheet() {
        bottomSheet.setContentView(R.layout.modal_bottom_sheet)
        when (mViewModel.filter.value) {
            "최신순" -> {
                checkRecent()
                mViewModel.setRcvRecent()
            }
            "인기순" -> {
                checkPopular()
                mViewModel.setRcvPopular()
            }
        }
        bottomSheet.recent_box.setOnClickListener {
            checkRecent()
            mViewModel.filter.value = "최신순"
            bottomSheet.dismiss()
        }
        bottomSheet.popular_box.setOnClickListener {
            checkPopular()
            mViewModel.filter.value = "인기순"
            bottomSheet.dismiss()
        }
        bottomSheet.close_bottom_sheet_btn.setOnClickListener {
            bottomSheet.dismiss()
        }
    }

    private fun checkRecent() {
        ThreeApplication.getInstance()
            .changeTextColor(
                blueText = bottomSheet.recent_txt,
                blackText = bottomSheet.popular_txt
            )

        ThreeApplication.getInstance().changeVisibleImage(
            showImage = bottomSheet.recent_check_img,
            hideImage = bottomSheet.popular_check_img
        )
    }

    private fun checkPopular() {
        ThreeApplication.getInstance()
            .changeTextColor(
                blueText = bottomSheet.popular_txt,
                blackText = bottomSheet.recent_txt
            )

        ThreeApplication.getInstance().changeVisibleImage(
            showImage = bottomSheet.popular_check_img,
            hideImage = bottomSheet.recent_check_img
        )
    }
}