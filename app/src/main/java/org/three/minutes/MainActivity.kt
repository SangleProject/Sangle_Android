package org.three.minutes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.three.minutes.databinding.ActivityMainBinding
import org.three.minutes.singleton.StatusObject
import org.three.minutes.util.keyBoardObserve

class MainActivity : AppCompatActivity() {

    private lateinit var mImm : InputMethodManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager

        //상태바 투명으로 만들기
        StatusObject.setStatusBar(this)

        this.keyBoardObserve {
            login_edt.clearFocus()
            password_edt.clearFocus()
        }


    }

    fun hideKeyboard() {
        mImm.hideSoftInputFromWindow(login_edt.windowToken, 0)
    }
}