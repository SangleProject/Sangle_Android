package org.three.minutes.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.singleton.StatusObject

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        StatusObject.setStatusBar(this)
        
//         fcm을 위한 현재 기기 토큰 받기
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful){
                return@OnCompleteListener
            }
            val token = task.result

            // DataStore에 DeviceToken 저장
            CoroutineScope(Main).launch{
                ThreeApplication.getInstance().getDataStore().setDeviceToken(token!!)
            }

//            CoroutineScope(Main).launch {
//                ThreeApplication.getInstance().getDataStore().deviceToken.collect {
//                    Log.e("DeviceToken",it)
//                }
//            }
        })


        CoroutineScope(Main).launch {
            splash_img.setAnimation("splash.json")
            delay(2000)
            var token = ""
            var refresh = ""
            launch{
                ThreeApplication.getInstance().getDataStore().token.collect { token = it }
                ThreeApplication.getInstance().getDataStore().refreshToken.collect { refresh = it }
            }.join()

            if (!token.isEmpty()){
                // do something
            }
            else{
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }


    }
}