package org.three.minutes.signup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    var progress = MutableLiveData<Int>()
    var email = MutableLiveData<String>("")

    init {
        progress.value = 0
    }

}