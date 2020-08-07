package org.three.minutes.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityMainBinding
import org.three.minutes.signup.ui.SignupActivity
import org.three.minutes.singleton.StatusObject
import org.three.minutes.util.keyBoardObserve

class MainActivity : AppCompatActivity() {

    private lateinit var mImm : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this@MainActivity,
            R.layout.activity_main
        )
        binding.activity = this
        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager

        //상태바 투명으로 만들기
        StatusObject.setStatusBar(this)

        //키보드가 내려가면 포커스 해제
        this.keyBoardObserve {
            login_edt.clearFocus()
            password_edt.clearFocus()
        }


    }

    // 레이아웃 클릭 시 키보드 내리기
    fun hideKeyboard() {
        if(mImm.isAcceptingText){
            mImm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
        }

    }

    // 로그인 클릭 시 확인 함수
    fun checkLogin(){
        Toast.makeText(this,"login",Toast.LENGTH_SHORT).show()
    }

    fun goToSignUp(){
        val intent = Intent(this,SignupActivity::class.java)
        startActivity(intent)
    }
}