package org.three.minutes.mypage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.three.minutes.mypage.data.MyWritingData

class MyPageViewModel : ViewModel(){

    var filter = MutableLiveData("최신순")
    var myId = MutableLiveData("머리가말랑말랑")
    var myIntro = MutableLiveData("소소한 일상을 나답게 살아가고자 글을 씁니다!")
    var myWritingList = mutableListOf<MyWritingData>()
}