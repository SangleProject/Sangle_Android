package org.three.minutes.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.three.minutes.home.data.CalendarData

class HomeViewModel : ViewModel(){

    //캘린더 날짜 정보가 들어있는 데이터 클래스 리스트
    var calendarData  = MutableLiveData<ArrayList<CalendarData>>(arrayListOf())

    //이번 주 쓴 글 증가 감소 추이
    var increDecre = MutableLiveData(0)
    var writingCount = MutableLiveData(0)
}