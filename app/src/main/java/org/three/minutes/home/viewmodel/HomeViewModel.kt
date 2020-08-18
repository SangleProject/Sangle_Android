package org.three.minutes.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel(){

    var lottie = MutableLiveData<String>("character_none.json")

    //이번 주 쓴 글 증가 감소 추이
    var increDecre = MutableLiveData(0)
    var writingCount = MutableLiveData(0)
}