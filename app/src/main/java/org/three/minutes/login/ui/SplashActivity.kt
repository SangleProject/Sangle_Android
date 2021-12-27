package org.three.minutes.login.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.asLiveData
import com.airbnb.lottie.LottieDrawable
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.home.ui.HomeActivity
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.signup.data.ResponseSignUpData
import org.three.minutes.singleton.StatusObject
import org.three.minutes.util.AppVersionChecker
import org.three.minutes.util.CustomDialog
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.showToast
import retrofit2.Call

class SplashActivity : AppCompatActivity() {

    private var token = ""
    private var refresh = ""
    private var onBoarding = true

    private val enforceDialog by lazy {
        CustomDialog(
            context = this,
            title = "업데이트 알림",
            content = "정상적인 앱 사용을 위해\n업데이트가 필요합니다.\n확인을 누르면 스토어로 이동합니다.",
            cancelTitle = "취소",
            okTitle = "확인",
            dialogImg = R.drawable.illust_popup02,
            isCancelable = false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        StatusObject.setStatusBar(this)
        initDialog()

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

        CoroutineScope(Main).launch {
            onBoarding = ThreeApplication.getInstance().getDataStore().isOnBoarding.first()
            splash_img.setAnimation("splash.json")
            splash_img.repeatCount = LottieDrawable.INFINITE
            delay(2000)
        }

        ThreeApplication.getInstance().getFireStore()
            .collection("updates")
            .document("enforceUpdate")
            .get()
            .addOnSuccessListener { document ->
                checkEnforceUpdate(document)
            }.addOnFailureListener {
                instanceData()
            }




    }

    private fun initDialog() {
        enforceDialog.setDialogClickListener(object : CustomDialog.ClickListener {
            override fun setOnOk(dialog: Dialog) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://play.google.com/store/apps/details?id=${packageName}")
                }
                startActivity(intent)
                moveTaskToBack(true); // 태스크를 백그라운드로 이동
                finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
            }

            override fun setOnCancel(dialog: Dialog) {
                moveTaskToBack(true); // 태스크를 백그라운드로 이동
                finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
            }
        })
    }

    private fun checkEnforceUpdate(document: DocumentSnapshot?) {
        if (document == null || document["android_version"] == null) {
            instanceData()
            return
        }

        val deviceUpdate = packageManager.getPackageInfo(packageName, 0).versionName.replace(".","").toInt()
        val updateVersion = document["android_version"].toString().
            replace(".","").toInt()

        if (updateVersion - deviceUpdate >= 10) {
            enforceDialog.show()
        } else {
            instanceData()
        }

    }

    private fun instanceData() {
        CoroutineScope(Main).launch {
            if (onBoarding) {
                ThreeApplication.getInstance().getDataStore().getDataStoreCore().edit {
                    it.clear()
                }
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                token = ThreeApplication.getInstance().getDataStore().token.first()
                refresh = ThreeApplication.getInstance().getDataStore().refreshToken.first()
                checkAutoLogin()
            }
        }
    }

    private fun checkAutoLogin() {
        if (token.isNotBlank()) {
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
                        showToast("서버 오류로 인해 자동 로그인에 실패했습니다.")
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
        } else {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}