package org.three.minutes.singleton

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

object GoogleLoginObject {

    lateinit var auth : FirebaseAuth
    lateinit var googleClient : GoogleSignInClient

    fun settingGoogle(activity : Activity, gso : GoogleSignInOptions){
        auth = FirebaseAuth.getInstance()
        googleClient = GoogleSignIn.getClient(activity, gso)
    }
}