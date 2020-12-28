package org.three.minutes.singleton

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

object GoogleLoginObject {

    lateinit var auth : FirebaseAuth
    lateinit var googleClient : GoogleSignInClient

    fun settingGoogle(activity : Activity, gso : GoogleSignInOptions){
        auth = FirebaseAuth.getInstance()
        googleClient = GoogleSignIn.getClient(activity, gso)
    }

    // 디바이스 토큰을 받는 함수
    fun getDeviceToken() : String{
        var token = ""
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if(!task.isSuccessful){
                return@OnCompleteListener
            }
            token = task.result!!
        })

        return token
    }
}