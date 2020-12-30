package org.three.minutes.signup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.three.minutes.signup.data.RequestGoogleSignUpData
import org.three.minutes.signup.data.RequestSignUpData

class SignUpViewModel(application: Application, private val useCase: SignUpUseCase)
    : AndroidViewModel(application) {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    private val context = getApplication<Application>().applicationContext

    var progress = MutableLiveData<Int>()
    var email = MutableLiveData("")
    var password = MutableLiveData("")
    var passwordCheck = MutableLiveData("")
    var age = MutableLiveData("")
    var gender = MutableLiveData<String>()
    var nickname = MutableLiveData("")

    var isGoogle = false
    var isGoHome = MutableLiveData(false)

    init {
        progress.value = 25
    }

    fun increaseProgress() {
        progress.value = progress.value?.plus(25)
    }

    fun decreaseProgress() {
        progress.value = progress.value?.minus(25)
    }

    // 이메일 체크
    fun callCheckEmail(nextPage: () -> Unit){
        useCase.checkEmail(context = context, nextPage = {nextPage()}, email = email.value!!)
    }

    // 회원가입 통신
    fun callSignUp(deviceToken : String) {
        val signUpData = RequestSignUpData(
            email = email.value!!,
            password = password.value!!,
            gender = gender.value!!,
            birth = age.value!!,
            nickName = nickname.value!!,
            deviceToken = deviceToken
        )
        val googleSignUpData = RequestGoogleSignUpData(
            email = email.value!!,
            gender = password.value!!,
            birth = age.value!!,
            nickName = nickname.value!!,
            deviceToken = deviceToken
        )

        useCase.callSignUp(
            context = context,
            isGoogle = isGoogle,
            isGoHome = isGoHome,
            requestSignUp = signUpData,
            requestGoogleSignUp = googleSignUpData)

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}