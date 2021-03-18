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
import kotlinx.coroutines.flow.collect
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.home.ui.HomeActivity
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.signup.data.ResponseSignUpData
import org.three.minutes.singleton.StatusObject
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.showToast
import retrofit2.Call

class SplashActivity : AppCompatActivity() {

    private var token = ""
    private var refresh = ""
    private var onBoarding = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        StatusObject.setStatusBar(this)

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

        ThreeApplication.getInstance().getDataStore().token.asLiveData()
            .observe(this@SplashActivity, {
                token = it
            })
        ThreeApplication.getInstance().getDataStore().refreshToken.asLiveData()
            .observe(this@SplashActivity, {
                refresh = it
            })
        CoroutineScope(IO).launch{
            ThreeApplication.getInstance().getDataStore().isOnBoarding.collect {
                onBoarding = it
            }
        }

        CoroutineScope(Main).launch {

            launch {
                splash_img.setAnimation("splash.json")
                splash_img.repeatCount = LottieDrawable.INFINITE
                delay(2000)
            }.join()

            if (onBoarding) {
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                checkAutoLogin()
            }
        }


    }

    private fun checkAutoLogin() {
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
                        finish()
                    },
                    onFailure = {
                        showToast("서버 오류로 인해 잠시 후 다시 시도해주세요.")
                        finishAndRemoveTask()
                        android.os.Process.killProcess(android.os.Process.myPid())
                    }
                )
        } else {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}