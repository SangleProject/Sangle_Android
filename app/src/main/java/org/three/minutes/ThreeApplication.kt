package org.three.minutes

import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.three.minutes.util.changeBlackColor
import org.three.minutes.util.changeBlueColor
import org.three.minutes.util.hideView
import org.three.minutes.util.showView
import org.w3c.dom.Text

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

    fun changeTextColor(blueText : TextView, blackText : TextView){
        blueText.changeBlueColor()
        blackText.changeBlackColor()
    }

    fun changeVisibleImage(showImage : ImageView, hideImage : ImageView){
        showImage.showView()
        hideImage.hideView()
    }



}