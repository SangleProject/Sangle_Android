package org.three.minutes.signup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_signup.*
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivitySignupBinding
import org.three.minutes.signup.adapter.ViewPagerAdapter
import org.three.minutes.signup.viewmodel.SignUpRepoImpl
import org.three.minutes.signup.viewmodel.SignUpUseCase
import org.three.minutes.signup.viewmodel.SignUpViewModel
import org.three.minutes.signup.viewmodel.SignUpViewModelFactory
import org.three.minutes.singleton.StatusObject
import org.three.minutes.singleton.PatternObject

class SignupActivity : AppCompatActivity() {
    private lateinit var mSignUpModel: SignUpViewModel
    private lateinit var mPageAdapter: ViewPagerAdapter
    private lateinit var mImm: InputMethodManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySignupBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup)

        val signUpImpl = SignUpRepoImpl()

        mSignUpModel = ViewModelProvider(this , SignUpViewModelFactory(ThreeApplication.getInstance(), SignUpUseCase(signUpImpl)))
            .get(SignUpViewModel::class.java)

        binding.lifecycleOwner = this
        binding.signupViewmodel = mSignUpModel
        binding.activity = this



        //상태바 투명으로 만들기
        StatusObject.setStatusBar(this)
        mImm = ThreeApplication.getInstance().getInputMethodManager()

        setSupportActionBar(signup_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //초기 버튼 클릭 방지
        signup_next_txt.isEnabled = false

        //이메일 체크 observe
        mSignUpModel.email.observe(this, Observer<String> { email ->
            val m = PatternObject.ePattern.matcher(email)
            signup_next_txt.isEnabled = m.matches()
        })


        initViewPager()

        //구글 로그인 경로로 들어왔는지 확인
        checkGoogle()

        //최하단 다음 버튼 클릭 시 다음페이지로 이동
        signup_next_txt.setOnClickListener {
            when (contents_viewpager.currentItem) {
                0 -> {
                    mSignUpModel.callCheckEmail {  nextPage() }
                }

                1-> {
                    nextPage()
                }

                2-> {
                    nextPage()
                    signup_next_txt.text = "시작하기"
                }
                else -> {
                    mSignUpModel.callSignUp()
                }
            }
        }

        //좌측 뒤로가기 버튼 클릭 시 이전 페이지로 이동
        back_img.setOnClickListener {
            if(!mSignUpModel.isGoogle){
                when (contents_viewpager.currentItem) {
                    0 -> {
                        finish()
                    }
                    else -> {
                        prePage()
                    }
                }
            }
            else{
                finish()
            }

        }
    }

    // 구글 버튼을 누르고 들어왔는지에 대한 체크
    private fun checkGoogle() {
        val intent = intent
        mSignUpModel.isGoogle = intent.getBooleanExtra("google",false)
        if(mSignUpModel.isGoogle){
            Log.e("Into Google","check OK")
            contents_viewpager.currentItem = 2

            // email 부분을 구글 이메일로 변경
            mSignUpModel.email.value =  intent.getStringExtra("googleId")
            mSignUpModel.increaseProgress()
            mSignUpModel.increaseProgress()
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

    private fun nextPage(){
        contents_viewpager.currentItem += 1
        signup_next_txt.isEnabled = false
        mSignUpModel.increaseProgress()
    }

    private fun prePage(){
        contents_viewpager.currentItem -= 1
        mSignUpModel.decreaseProgress()
    }

}