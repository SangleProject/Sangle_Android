package org.three.minutes.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel(){
    var introduce = MutableLiveData("")
    var introduceCount = MutableLiveData(0)
}