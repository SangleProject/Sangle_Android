package org.three.minutes.signup.viewmodel

import android.content.Context

interface SignUpRepository {
    fun callCheckEmailAPI(context : Context, nextPage : () -> Unit, email : String)
    fun callCheckNickNameAPI(nickName: String, isAvailable : () -> Unit, isNotAvailable : ()-> Unit)
}