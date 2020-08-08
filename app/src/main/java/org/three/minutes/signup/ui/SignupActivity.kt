package org.three.minutes.signup.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_signup.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivitySignupBinding
import org.three.minutes.signup.adapter.ViewPagerAdapter
import org.three.minutes.signup.viewmodel.SignUpViewModel
import org.three.minutes.singleton.StatusObject
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {
    private val mSignUpModel : SignUpViewModel by viewModels()
    private lateinit var mPageAdapter : ViewPagerAdapter
    private lateinit var mImm : InputMethodManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivitySignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup)
        binding.lifecycleOwner = this
        binding.signupViewmodel = mSignUpModel
        binding.activity = this

        //이메일 체크 패턴
        val p = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
        )

        //상태바 투명으로 만들기
        StatusObject.setStatusBar(this)
        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager

        setSupportActionBar(signup_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //이메일 체크 observe
        mSignUpModel.email.observe(this, Observer<String> {email ->
            val m = p.matcher(email)
                signup_next_txt.isEnabled = m.matches()
        })


        initViewPager()

        //최하단 다음 버튼 클릭 시 다음페이지로 이동
        signup_next_txt.setOnClickListener {
            when(contents_viewpager.currentItem){
                0 -> {
                    contents_viewpager.currentItem += 1
                    signup_next_txt.isEnabled = false
                    mSignUpModel.increaseProgress()
                }
            }
        }
    }

    private fun initViewPager() {
        mPageAdapter = ViewPagerAdapter(supportFragmentManager)
        contents_viewpager.adapter = mPageAdapter
    }

    // x 버튼 누르면 액티비티 종료
    fun finishActivity(){
        finish()
    }

    //프래그먼트에 키보드 내리는 InputMethodManager 전달
    fun getMethodManager() : InputMethodManager = mImm
}