package org.three.minutes.profile.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivityProfileChangeBinding
import org.three.minutes.profile.adapter.ProfileChangeAdapter
import org.three.minutes.profile.data.RequestProfileData
import org.three.minutes.profile.viewmodel.ProfileViewModel
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.showToast

class ProfileChangeActivity : AppCompatActivity() {
    private val mBinding: ActivityProfileChangeBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_profile_change)
    }
    private val mViewModel: ProfileViewModel by viewModels()
    private lateinit var mImm: InputMethodManager
    private lateinit var rcvAdpater: ProfileChangeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.getToken.observe(this, {
            mViewModel.token = it
        })
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        mImm = ThreeApplication.getInstance().getInputMethodManager()
        mViewModel.callMyInfo()
        setObserve()

        mBinding.apply {
            lifecycleOwner = this@ProfileChangeActivity
            viewModel = mViewModel
        }

        // 키보드 내리면서 포커스 해제
        mBinding.layoutContainer.setOnClickListener {
            clearFocus()
        }

        TedKeyboardObserver(this)
            .listen { isShow ->
                if (!isShow) {
                    clearFocus()
                }
            }

        // 우측 상단 저장 버튼 클릭 이벤트
        mBinding.profileChangedTxt.setOnClickListener {
            if (mViewModel.isOk) {
                SangleServiceImpl.service.putProfileChange(
                    mViewModel.token,
                    RequestProfileData(
                        nickName = mViewModel.profileName.value!!,
                        info = mViewModel.introduce.value!!,
                        profileImg = (rcvAdpater.checkedPosition + 1).toString()
                    )
                )
                    .customEnqueue(
                        onSuccess = {
                            val intent = Intent()
                            setResult(RESULT_OK, intent)
                            finish()
                        },
                        onError = {
                            Log.e("ProfileChangeActivity", "putProfileChange ERROR : ${it.code()}")
                        }
                    )
            } else {
                showToast("닉네임을 입력해주세요.")
            }
        }

        // 프로필 선택 Single Selection Rcv
        setRcv()


    }

    private fun setObserve() {

        // 닉네임을 채워야지만 저장 가능
        mViewModel.profileName.observe(this, {
            mViewModel.isOk = it.isNotEmpty()
        })
        mViewModel.isCalledProfile.observe(this, {
            if (it) {
                mBinding.profileSelectRcv.adapter = rcvAdpater
                rcvAdpater.checkedPosition = mViewModel.imgIndex.value!!
                rcvAdpater.profileList = mViewModel.profileImgList
                rcvAdpater.notifyDataSetChanged()
            }
        })
        // 소개글 글자 수 카운팅 observer
        mViewModel.introduce.observe(this, { introduce ->
            if (introduce != null){
                mViewModel.introduceCount.value = introduce.length
            }
        })
    }

    private fun setRcv() {
        rcvAdpater = ProfileChangeAdapter(this)
    }


    private fun clearFocus() {
        if (mImm.isAcceptingText) {
            mImm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
        }
        mBinding.apply {
            profileIntroduceEdt.clearFocus()
            profileNicknameEdt.clearFocus()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent()
        setResult(RESULT_CANCELED, intent)
        finish()
    }
}