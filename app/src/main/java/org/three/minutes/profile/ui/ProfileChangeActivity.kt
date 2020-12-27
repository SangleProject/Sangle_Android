package org.three.minutes.profile.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivityProfileChangeBinding
import org.three.minutes.profile.viewmodel.ProfileViewModel

class ProfileChangeActivity : AppCompatActivity() {
    private val mBinding : ActivityProfileChangeBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_profile_change)
    }
    private val mViewModel : ProfileViewModel by viewModels()
    private lateinit var mImm : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_profile_change)
        mImm = ThreeApplication.getInstance().getInputMethodManager()

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
                if (!isShow){
                    clearFocus()
                }
            }

        // 우측 상단 저장 버튼 클릭 이벤트
        mBinding.profileChangedTxt.setOnClickListener {
            val intent = Intent()
            setResult(RESULT_OK,intent)
            finish()
        }

        // 소개글 글자 수 카운팅 observer
        mViewModel.introduce.observe(this,{introduce ->
            mViewModel.introduceCount.value = introduce.length
            Log.d("IntroduceCount", "${introduce.length}")
        })

        // 프로필 설정 체크박스 이벤트 리스너 설정
        setProfileState()


    }

    private fun setProfileState() {

        mBinding.profileImgGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.select_profile1 ->{
                }
                R.id.select_profile2 ->{

                }
                R.id.select_profile3 ->{

                }
                R.id.select_profile4 ->{

                }
                R.id.select_profile5 ->{

                }
                R.id.select_profile6 ->{

                }
                R.id.select_profile7 ->{

                }
                R.id.select_profile8 ->{

                }
            }
        }
    }

    private fun clearFocus(){
        if(mImm.isAcceptingText){
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