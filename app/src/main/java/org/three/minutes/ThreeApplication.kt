package org.three.minutes

import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import org.three.minutes.util.*

class ThreeApplication : Application(){
    private lateinit var mImm : InputMethodManager
    private lateinit var dataStore : SangleDataStoreManager

    companion object{
        private lateinit var threeApplication : ThreeApplication
        fun getInstance() : ThreeApplication = threeApplication
    }

    override fun onCreate() {
        super.onCreate()
        threeApplication = this
        mImm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        dataStore = SangleDataStoreManager(this)
    }

    fun getInputMethodManager() : InputMethodManager = mImm
    fun getDataStore() : SangleDataStoreManager = dataStore

    fun changeTextColor(blueText : TextView, blackText : TextView){
        blueText.changeBlueColor()
        blackText.changeBlackColor()
    }

    fun changeVisibleImage(showImage : ImageView, hideImage : ImageView){
        showImage.showView()
        hideImage.hideView()
    }



}