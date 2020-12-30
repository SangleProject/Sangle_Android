package org.three.minutes.signup.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.signup.data.RequestCheckEmailData
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.showToast

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

    // 닉네임 중복 체크
    fun callCheckNickNameAPI(context: Context) {

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}