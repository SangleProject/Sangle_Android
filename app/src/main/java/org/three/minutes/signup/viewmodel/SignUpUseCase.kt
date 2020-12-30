package org.three.minutes.signup.viewmodel

import android.content.Context
import org.three.minutes.util.showToast

class SignUpUseCase(private val signUpImpl: SignUpRepository) {

    fun checkEmail(context : Context , nextPage : () -> Unit, email : String){
        signUpImpl.callCheckEmailAPI(context , nextPage, email)
    }

    fun callSignUp(context : Context, nickName : String){
        // 먼저 닉네임 중복 체크
        signUpImpl.callCheckNickNameAPI(nickName = nickName,
            isAvailable =
            {
                context.showToast("가입 완료~")
            },
            isNotAvailable ={
                context.showToast("중복!")
            })
    }

}