package org.three.minutes.singleton

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

object GoogleLoginObject {

    enum class GoogleLogInCode(val code : Int){
        LOG_OUT_CODE(-100),
        NOT_GOOGLE_LOGIN_CODE(-200)
    }

    lateinit var auth : FirebaseAuth
    lateinit var googleClient : GoogleSignInClient

    fun settingGoogle(activity : Activity, gso : GoogleSignInOptions){
        auth = FirebaseAuth.getInstance()
        googleClient = GoogleSignIn.getClient(activity, gso)
    }
}