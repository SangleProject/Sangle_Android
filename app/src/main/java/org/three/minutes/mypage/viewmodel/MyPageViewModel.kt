package org.three.minutes.mypage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.three.minutes.mypage.data.MyWritingData

class MyPageViewModel : ViewModel(){

    var filter = MutableLiveData("최신순")
    var myWritingList = mutableListOf<MyWritingData>()
}