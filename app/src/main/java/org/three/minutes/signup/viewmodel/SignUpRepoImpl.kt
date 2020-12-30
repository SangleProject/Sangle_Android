package org.three.minutes.signup.viewmodel

import android.content.Context
import android.util.Log
import org.three.minutes.login.data.RequestGoogleLoginData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.signup.data.RequestCheckEmailData
import org.three.minutes.signup.data.RequestCheckNickNameData
import org.three.minutes.signup.data.RequestSignUpData
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.showToast

class SignUpRepoImpl() : SignUpRepository {
    // 이메일 체크 api
    override fun callCheckEmailAPI(context: Context, nextPage: () -> Unit, email: String) {
        SangleServiceImpl.service.postCheckEmail(
            RequestCheckEmailData(email = email)
        ).customEnqueue(
            onSuccess = {
                if (it.isCheck) {
                    nextPage()
                } else {
                    context.showToast("중복된 이메일 입니다.")
                }
            }
        )
    }

    // 닉네임 중복 체크 api
    override fun callCheckNickNameAPI(nickName: String, isAvailable : () -> Unit, isNotAvailable : ()-> Unit){
        SangleServiceImpl.service.postCheckNickName(RequestCheckNickNameData(nickName))
            .customEnqueue(
                onSuccess = {
                    if (it.isCheck){
                        isAvailable()
                    }
                    else{
                        isNotAvailable()
                    }
                },
                onError = {
                    Log.e("CheckNickName", "${it.code()}")
                }
            )

    }
}