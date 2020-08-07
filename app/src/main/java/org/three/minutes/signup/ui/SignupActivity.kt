package org.three.minutes.signup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_signup.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivitySignupBinding
import org.three.minutes.signup.viewmodel.SignUpViewModel
import org.three.minutes.singleton.StatusObject

class SignupActivity : AppCompatActivity() {
    private val mSignUpModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivitySignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup)
        binding.signupViewmodel = mSignUpModel

        //상태바 투명으로 만들기
        StatusObject.setStatusBar(this)

        setSupportActionBar(signup_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mSignUpModel.email.observe(this, Observer<String> {email ->
            signup_next_txt.isEnabled = email.length >10
        })

        back_img.setOnClickListener {
            Toast.makeText(this, "back",Toast.LENGTH_SHORT).show()
        }
    }
}