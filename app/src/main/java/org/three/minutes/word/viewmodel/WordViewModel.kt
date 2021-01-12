package org.three.minutes.word.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.ThreeApplication
import org.three.minutes.home.data.ResponseTodayTopicData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import org.three.minutes.word.data.ResponseLastTopicData
import org.three.minutes.word.data.ResponseSearchData
import org.three.minutes.word.data.SearchWritingData

class WordViewModel() : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    var getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""

    var todayTopicList = MutableLiveData<List<ResponseTodayTopicData>>(listOf())
    var lastTopicList = MutableLiveData<List<ResponseLastTopicData>>(listOf())

    // 라디오 버튼 체크
    var allCheck = MutableLiveData(true)
    var doneCheck = MutableLiveData(false)
    var notCheck = MutableLiveData(false)

    // 검색 관련 데이터 모음
    var searchWord = MutableLiveData("")
    var filter = MutableLiveData("최신순")
    var searchResultList = MutableLiveData<List<ResponseSearchData>>(listOf())
    var searchCount = MutableLiveData(126)

    fun callTopic() {
        viewModelScope.launch {
            SangleServiceImpl.service.getTodayTopic(token = token)
                .customEnqueue(
                    onSuccess = {
                        todayTopicList.value = it
                    },
                    onError = {
                        Log.e("WordActivity", "callTodayTopic() error : ${it.code()}")
                    }
                )

            SangleServiceImpl.service.getLastTopic(token = token)
                .customEnqueue(
                    onSuccess = {
                        lastTopicList.value = it
                    },
                    onError = {
                        Log.e("WordActivity", "callTodayTopic() LastTopic error : ${it.code()}")
                    }
                )
        }
    }

    fun callSearchRecent(){
        viewModelScope.launch {
            SangleServiceImpl.service.getTopicSearchRecent(token = token, topic = searchWord.value!!)
                .customEnqueue(
                    onSuccess = {
                        searchResultList.value = it
                        searchCount.value = it.size
                    },
                    onError = {
                        Log.e("WordActivity", "callSearchRecent() error : ${it.code()}")
                    }
                )
        }
    }
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}