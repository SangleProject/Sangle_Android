package org.three.minutes.signup.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SignUpViewModelFactory(val application : Application, val useCase: SignUpUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(SignUpViewModel::class.java)){
            SignUpViewModel(application , useCase) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}