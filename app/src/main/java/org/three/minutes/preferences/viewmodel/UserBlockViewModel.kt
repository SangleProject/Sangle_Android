package org.three.minutes.preferences.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserBlockViewModel : ViewModel() {


    private val blockedUsers = MutableLiveData<String>()
    val blockedUsersLiveData: LiveData<String> = blockedUsers

}