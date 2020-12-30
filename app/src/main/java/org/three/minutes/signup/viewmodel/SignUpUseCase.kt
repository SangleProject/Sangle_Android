package org.three.minutes.signup.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.three.minutes.ThreeApplication
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.signup.data.RequestGoogleSignUpData
import org.three.minutes.signup.data.RequestSignUpData
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.showToast

class SignUpUseCase(private val signUpImpl: SignUpRepository) {

    fun checkEmail(context: Context, nextPage: () -> Unit, email: String) {
        signUpImpl.callCheckEmailAPI(context, nextPage, email)
    }

    fun callSignUp(
        context: Context,
        isGoogle: Boolean,
        isGoHome : MutableLiveData<Boolean>,
        requestSignUp: RequestSignUpData,
        requestGoogleSignUp: RequestGoogleSignUpData
    ) {
        // 먼저 닉네임 중복 체크
        signUpImpl.callCheckNickNameAPI(nickName = requestSignUp.nickName,
            isAvailable =
            {
                if (isGoogle) {
                    googleSignUp(requestGoogleSignUp)
                } else {
                    signUp(requestSignUp, isGoHome)
                }
            },
            isNotAvailable = {
                context.showToast("중복!")
            })
    }

    private fun googleSignUp(request : RequestGoogleSignUpData) {
    }

    private fun signUp(request: RequestSignUpData, isGoHome: MutableLiveData<Boolean>) {
        SangleServiceImpl.service.postSignUp(request)
            .customEnqueue(
                onSuccess = {
                    CoroutineScope(Dispatchers.Default).launch{
                        ThreeApplication.getInstance().getDataStore().setToken(it.token)
                        ThreeApplication.getInstance().getDataStore().setRefreshToken(it.refresh)
                        Log.e("DataStore", "Set Token OK")
                    }
                    isGoHome.value = true
                },
                onError = {
                    Log.e("SignUpApi", "${it.code()}")
                }
            )
    }

}