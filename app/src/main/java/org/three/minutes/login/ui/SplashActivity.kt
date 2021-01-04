package org.three.minutes.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.asLiveData
import com.airbnb.lottie.LottieDrawable
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.home.ui.HomeActivity
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.singleton.StatusObject
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.showToast

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        StatusObject.setStatusBar(this)

        var token = ""
        var refresh = ""

//         fcm을 위한 현재 기기 토큰 받기
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val deviceToken = task.result

            // DataStore에 DeviceToken 저장
            CoroutineScope(IO).launch {
                ThreeApplication.getInstance().getDataStore().setDeviceToken(deviceToken!!)
            }

        })

//        CoroutineScope(Main).launch {
//            ThreeApplication.getInstance().getDataStore().token.collect {
//                token = it
//                Log.e("AutoLogin", "token : $it")
//            }
//        }

        ThreeApplication.getInstance().getDataStore().token.asLiveData().observe(this@SplashActivity,{
            token = it
        })
        ThreeApplication.getInstance().getDataStore().refreshToken.asLiveData().observe(this@SplashActivity,{
            refresh = it
        })

        CoroutineScope(Main).launch {

            launch{
                splash_img.setAnimation("splash.json")
                splash_img.repeatCount = LottieDrawable.INFINITE
                delay(2000)
            }.join()


            if (token.isNotEmpty()) {
                // do something
                SangleServiceImpl.service.getToken(refresh)
                    .customEnqueue(
                        onSuccess = {
                            CoroutineScope(IO).launch {
                                ThreeApplication.getInstance().getDataStore()
                                    .setReTokens(it.token, it.refresh)
                            }

                            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        },
                        onError = {
                            showToast("자동 로그인에 실패하였습니다. : ${it.code()}")
                            val intent = Intent(this@SplashActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    )
            } else {
                Log.e("SplashActivity","Auto Login Fail : Empty Token")
                Log.e("SplashActivity","Auto Login Fail : Token - $token")
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }

        }



    }
}