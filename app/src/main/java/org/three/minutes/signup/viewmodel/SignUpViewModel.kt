package org.three.minutes.signup.viewmodel

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    var progress = MutableLiveData<Int>()
    var email = MutableLiveData<String>("")
    var password = MutableLiveData<String>("")
    var passwordCheck = MutableLiveData<String>("")
    var age = MutableLiveData<String>("")
    var gender = MutableLiveData<Int>()
    var nickname = MutableLiveData<String>("")

    init {
        progress.value = 25
    }

    fun increaseProgress() {
        progress.value = progress.value?.plus(25)
    }

    fun decreaseProgress() {
        progress.value = progress.value?.minus(25)
    }

}