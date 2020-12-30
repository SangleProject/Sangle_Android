package org.three.minutes.signup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

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
    fun callSignUp() {
        useCase.callSignUp(context = context , nickName = nickname.value!!)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}