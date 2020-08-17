package org.three.minutes.login.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityMainBinding
import org.three.minutes.home.ui.HomeActiviy
import org.three.minutes.signup.ui.SignupActivity
import org.three.minutes.singleton.StatusObject


class MainActivity : AppCompatActivity() {

    private lateinit var mImm : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
//        with(window){
//            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
//            allowEnterTransitionOverlap = true
//
////            //호출되는(intent) 화면의 애니메이션
////            enterTransition = Slide(Gravity.END)
//            //호출하는(MainActivity) 화면의 애니메이션
//            exitTransition = Slide(Gravity.START)
//
//        }

        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.activity = this
        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

        //상태바 투명으로 만들기
        StatusObject.setStatusBar(this)


    }

    // 레이아웃 클릭 시 키보드 내리기
    fun hideKeyboard() {
        //키보드가 활성화 되어있는지 확인 -> isAcceptingText
        if(mImm.isAcceptingText){
            login_edt.clearFocus()
            password_edt.clearFocus()
            mImm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
        }

    }

    // 로그인 클릭 시 확인 함수
    fun checkLogin(){
        val intent = Intent(this, HomeActiviy::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_hold)
    }

    fun goToSignUp(){
        val intent = Intent(this,SignupActivity::class.java)
        startActivity(intent)
    }
}