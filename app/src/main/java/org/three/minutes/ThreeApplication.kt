package org.three.minutes

import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import org.three.minutes.mypage.ui.MyBottomSheet

class ThreeApplication : Application(){
    private lateinit var mImm : InputMethodManager

    companion object{
        private lateinit var threeApplication : ThreeApplication
        fun getInstance() : ThreeApplication = threeApplication
    }

    override fun onCreate() {
        super.onCreate()
        threeApplication = this
        mImm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    }

    fun getInputMethodManager() : InputMethodManager = mImm

}