package org.three.minutes.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.three.minutes.home.data.CalendarData
import java.util.*

class HomeViewModel : ViewModel(){

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    //캘린더 날짜 정보가 들어있는 데이터 클래스 리스트
    var arrayCalendar = arrayListOf<CalendarData>()
    var year = MutableLiveData(0)
    val month = MutableLiveData(0)

    //이번 주 쓴 글 증가 감소 추이
    var increDecre = MutableLiveData(0)
    var writingCount = MutableLiveData(0)

    fun settingDate(){
        viewModelScope.launch {
                addDayData()
        }
    }

    private suspend fun addDayData(){
        val today = GregorianCalendar()
        val emptyDay = today.get(Calendar.DAY_OF_WEEK) - 1 // 비어 있는 요일
        val max = today.getActualMaximum(Calendar.DAY_OF_MONTH) //마지막 날짜

        for(i in 0..emptyDay){
            arrayCalendar.add(CalendarData(0,0,0,true))
        }

        val y = today.get(Calendar.YEAR)
        val m = today.get(Calendar.MONTH) + 1 // 월 표시는 0 ~ 11

        year.value = y
        month.value = m

        for(i in 1..max){
            arrayCalendar.add(CalendarData(y,m,i,false))
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}