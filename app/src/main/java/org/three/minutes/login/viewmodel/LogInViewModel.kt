package org.three.minutes.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class LogInViewModel() : ViewModel() {
    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}