package org.three.minutes

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.three.minutes.util.*

@HiltAndroidApp
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

//        FirebaseMessaging.getInstance().subscribeToTopic("test")
        CoroutineScope(Dispatchers.IO).launch {

            val notice = dataStore.isNotification.first()
            val motive = dataStore.isMotive.first()

            if (notice){
                FirebaseMessaging.getInstance().subscribeToTopic("notice")
            }
            else{
                FirebaseMessaging.getInstance().unsubscribeFromTopic("notice")
            }

            if (motive){
                FirebaseMessaging.getInstance().subscribeToTopic("motive")
            }
            else{
                FirebaseMessaging.getInstance().unsubscribeFromTopic("motive")
            }
        }
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