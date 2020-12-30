package org.three.minutes.signup.viewmodel

import android.content.Context

class SignUpUseCase(private val signUpImpl: SignUpRepository) {

    fun checkEmail(context : Context , nextPage : () -> Unit, email : String){
        signUpImpl.callCheckEmailAPI(context , nextPage, email)
    }

}