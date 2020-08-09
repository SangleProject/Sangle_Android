package org.three.minutes.signup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.fragment_password.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivitySignupBinding
import org.three.minutes.signup.adapter.ViewPagerAdapter
import org.three.minutes.signup.viewmodel.SignUpViewModel
import org.three.minutes.singleton.StatusObject
import org.three.minutes.util.PatternObject

class SignupActivity : AppCompatActivity() {
    private val mSignUpModel: SignUpViewModel by viewModels()
    private lateinit var mPageAdapter: ViewPagerAdapter
    private lateinit var mImm: InputMethodManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySignupBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding.lifecycleOwner = this
        binding.signupViewmodel = mSignUpModel
        binding.activity = this

        //상태바 투명으로 만들기
        StatusObject.setStatusBar(this)
        mImm =
            getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

        setSupportActionBar(signup_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //이메일 체크 observe
        mSignUpModel.email.observe(this, Observer<String> { email ->
            val m = PatternObject.ePattern.matcher(email)
            signup_next_txt.isEnabled = m.matches()
        })


        //패스워드 체크 조건 observe
//        mSignUpModel.passwordCheck.observe(this, Observer<String> { passwordCheck ->
//            signup_next_txt.isEnabled = passwordCheck == mSignUpModel.password.toString()
//        })


        initViewPager()

        //최하단 다음 버튼 클릭 시 다음페이지로 이동
        signup_next_txt.setOnClickListener {
            when (contents_viewpager.currentItem) {
                0 -> {
                    contents_viewpager.currentItem += 1
                    signup_next_txt.isEnabled = false
                    mSignUpModel.increaseProgress()
                }
            }
        }

        //좌측 뒤로가기 버튼 클릭 시 이전 페이지로 이동
        back_img.setOnClickListener {
            when (contents_viewpager.currentItem) {
                0 -> {
                    finish()
                }

                1 -> {
                    contents_viewpager.currentItem -= 1
                    mSignUpModel.decreaseProgress()
                }
            }
        }
    }

    private fun initViewPager() {
        mPageAdapter = ViewPagerAdapter(supportFragmentManager)
        contents_viewpager.adapter = mPageAdapter
    }

    // x 버튼 누르면 액티비티 종료
    fun finishActivity() {
        finish()
    }

}