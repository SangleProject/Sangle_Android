package org.three.minutes.signup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    var progress = MutableLiveData<Int>()
    var email = MutableLiveData<String>("")

    init {
        progress.value = 25
    }

    fun increaseProgress(){
        progress.value = progress.value?.plus(25)
    }
    fun decreaseProgress(){
        progress.value = progress.value?.minus(25)
    }
}