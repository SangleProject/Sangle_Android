package org.three.minutes.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel(){

    var lottie = MutableLiveData<String>("character_none.json")
}