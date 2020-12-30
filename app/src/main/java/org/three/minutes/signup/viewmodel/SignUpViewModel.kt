package org.three.minutes.signup.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.three.minutes.server.SangleServiceImpl

class SignUpViewModel : ViewModel() {
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

    fun callCheckEmailAPI(context : Context){
    }

}