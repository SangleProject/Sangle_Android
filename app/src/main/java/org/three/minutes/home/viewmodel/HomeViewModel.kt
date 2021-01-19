package org.three.minutes.home.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.three.minutes.detail.data.ResponseMyWritingData
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.home.data.CalendarData
import org.three.minutes.home.data.ResponseFameData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import java.util.*

class HomeViewModel(application: Application, private val useCase: HomeUseCase) :
    AndroidViewModel(application) {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    private val context = getApplication<Application>().applicationContext

    //캘린더 날짜 정보가 들어있는 데이터 클래스 리스트
    var arrayCalendar = arrayListOf<CalendarData>()
    var year = MutableLiveData(0)
    val month = MutableLiveData(0)
    var isCalendarComplete = MutableLiveData(false)

    //token값
    var token: String = ""

    // 글 쓸 topic
    var topic = MutableLiveData<String>()

    //메인 화면에 띄워놓을 데이터들
    //아이디
    var nickName = MutableLiveData("")

    //프로필이미지
    var profileImg = MutableLiveData("")

    //유저 전체 글 작성 수
    var postCount = MutableLiveData(0)

    //유저 이번 주 글 작성 수
    var week = MutableLiveData(0)

    //유저 오늘 남은 글 갯수
    var remaining = MutableLiveData(0)

    //유저 오늘 글 쓴 갯수
    var percentage = MutableLiveData(0)

    //저번주 글 작성 비교 increase / same / decrease
    var compare = MutableLiveData("")

    // 명예의 전당 데이터
    var isFameComplete = MutableLiveData(false)
    var fameDataList = MutableLiveData<List<ResponseFameData>>(listOf())

    // My 서랍 페이지 내가 쓴 글 데이터, 담은 글 데이터
    var myPostListRecent = MutableLiveData<List<ResponseMyWritingData>>(listOf())
    var myPostListPopular = MutableLiveData<List<ResponseMyWritingData>>(listOf())
    var myScrapListRecent = MutableLiveData<List<ResponseOtherWritingData>>(listOf())
    var myScrapListPopular = MutableLiveData<List<ResponseOtherWritingData>>(listOf())

    fun settingDate() {
        viewModelScope.launch {
            addDayData()
        }
    }

    private suspend fun addDayData() {
        val today = GregorianCalendar()
        val emptyDay = today.get(Calendar.DAY_OF_WEEK) - 1 // 비어 있는 요일
        val max = today.getActualMaximum(Calendar.DAY_OF_MONTH) //마지막 날짜

        for (i in 0..emptyDay) {
            arrayCalendar.add(CalendarData(0, 0, 0, true))
        }

        val y = today.get(Calendar.YEAR)
        val m = today.get(Calendar.MONTH) + 1 // 월 표시는 0 ~ 11

        year.value = y
        month.value = m

        for (i in 1..max) {
            arrayCalendar.add(CalendarData(y, m, i, false))
        }
        isCalendarComplete.postValue(true)
    }

    fun setInfo() {
        viewModelScope.launch {
            SangleServiceImpl.service.getMainInfo(token)
                .customEnqueue(
                    onSuccess = {
                        nickName.postValue(it.nickName)
                        profileImg.postValue(it.profileImg)
                        postCount.postValue(it.postCount)
                        week.postValue(it.week)
                        compare.postValue(it.compare)
                        remaining.postValue(it.remaining)
                        percentage.postValue(it.percentage)
                    },
                    onError = {
                        Log.e("ERROR in MainInfo API", "${it.code()}")
                    }
                )
        }
    }

    fun callTopic() {
        useCase.goToWriting(token, topic)
    }

    fun callFameData() {
        viewModelScope.launch {
            SangleServiceImpl.service.getFameData(token)
                .customEnqueue(
                    onSuccess = {
                        fameDataList.value = it
                        isFameComplete.value = true
                    },
                    onError = {
                        Log.e("HomeActivity", "fun callFameData() error : ${it.code()}")
                    }
                )
        }
    }

    // My 서랍에서 보여질 데이터 통신
    fun callMyData() {
        viewModelScope.launch {
            SangleServiceImpl.service.getMyPostRecent(token)
                .customEnqueue(
                    onSuccess = {
                        myPostListRecent.value = it
                    },
                    onError = {
                        Log.e("HomeActivity", "fun myPostRecent() error : ${it.code()}")
                    }
                )
            SangleServiceImpl.service.getMyPostPopular(token)
                .customEnqueue(
                    onSuccess = {
                        myPostListPopular.value = it
                    },
                    onError = {
                        Log.e("HomeActivity", "fun myPostPopular() error : ${it.code()}")
                    }
                )

            SangleServiceImpl.service.getMyScrapRecent(token)
                .customEnqueue(
                    onSuccess = {
                        myScrapListRecent.value = it
                    },
                    onError = {
                        Log.e("HomeActivity", "fun myScrapRecent() error : ${it.code()}")
                    }
                )

            SangleServiceImpl.service.getMyScrapPopular(token)
                .customEnqueue(
                    onSuccess = {
                        myScrapListPopular.value = it
                    },
                    onError = {
                        Log.e("HomeActivity", "fun myScrapRecent() error : ${it.code()}")
                    }
                )
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}