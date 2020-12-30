package org.three.minutes.signup.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.signup.data.RequestCheckEmailData
import org.three.minutes.util.customEnqueue

class SignUpViewModel : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

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

    fun callCheckEmailAPI() : Boolean{
        var check = false
        viewModelScope.launch {
            launch {
                SangleServiceImpl.service.postCheckEmail(
                    RequestCheckEmailData(email = email.value!!)
                ).customEnqueue(
                    onSuccess = {
                        check = it.isCheck
                    }
                )
            }.join()
        }

        return check
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}