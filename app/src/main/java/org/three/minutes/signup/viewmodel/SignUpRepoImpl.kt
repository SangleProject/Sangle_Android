package org.three.minutes.signup.viewmodel

import android.content.Context
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.signup.data.RequestCheckEmailData
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.showToast

class SignUpRepoImpl() : SignUpRepository {

    override fun callCheckEmailAPI(context: Context, nextPage: () -> Unit, email : String) {
        SangleServiceImpl.service.postCheckEmail(
            RequestCheckEmailData(email = email)
        ).customEnqueue(
            onSuccess = {
                if(it.isCheck){
                    nextPage()
                }
                else{
                    context.showToast("중복된 이메일 입니다.")
                }
            }
        )
    }

    override fun callCheckNickNameAPI(context: Context) {
    }
}