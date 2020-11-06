package org.three.minutes.mypage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyPageViewModel : ViewModel(){

    var filter = MutableLiveData("최신순")
}