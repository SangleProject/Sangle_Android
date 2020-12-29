package org.three.minutes.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogInViewModel() : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
}