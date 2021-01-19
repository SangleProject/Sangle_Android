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
import org.three.minutes.word.data.ResponsePastSearchData
import org.three.minutes.word.data.ResponseSearchTopicData

class WordViewModel : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    var getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""
    var isKeyboardShow = false

    var todayTopicList = MutableLiveData<List<ResponseTodayTopicData>>(listOf())
    var lastTopicList = MutableLiveData<List<ResponseLastTopicData>>(listOf())

    // 라디오 버튼 체크
    var allCheck = MutableLiveData(true)
    var doneCheck = MutableLiveData(false)
    var notCheck = MutableLiveData(false)

    // 지난 글감 리스트 데이터 모음
    var lastDetailTopic = MutableLiveData("")
    var lastTopicDetailList = MutableLiveData<List<ResponsePastSearchData>>(listOf())
    var lastTopicDetailCount = MutableLiveData(0)
    var lastTopicOk = MutableLiveData(false)
    var filter = MutableLiveData("최신순")

    // 검색 다시 만드는 데이터
    var searchWord = MutableLiveData("")
    var searchResultTopicList = MutableLiveData<List<ResponseSearchTopicData>>(listOf())


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
                        lastTopicOk.value = true
                    },
                    onError = {
                        Log.e("WordActivity", "callTodayTopic() LastTopic error : ${it.code()}")
                    }
                )
        }
    }

    fun callPastDetailRecent(pastTopic: String) {
        viewModelScope.launch {
            SangleServiceImpl.service.getTopicSearchRecent(
                token = token,
                topic = pastTopic
            ).customEnqueue(
                onSuccess = {
                    lastDetailTopic.value = pastTopic
                    lastTopicDetailList.value = it
                    lastTopicDetailCount.value = it.size
                },
                onError = {
                    Log.e("WordActivity", "callPastDetailRecent() error : ${it.code()}")
                }
            )
        }
    }

    fun callPastDetailPopular(pastTopic: String) {
        viewModelScope.launch {
            SangleServiceImpl.service.getTopicSearchPopular(
                token = token,
                topic = pastTopic
            ).customEnqueue(
                onSuccess = {
                    lastDetailTopic.value = pastTopic
                    lastTopicDetailList.value = it
                    lastTopicDetailCount.value = it.size
                },
                onError = {
                    Log.e("WordActivity", "callPastDetailPopular() error : ${it.code()}")
                }
            )
        }
    }

    fun callSearchTopic() {
        viewModelScope.launch {
            SangleServiceImpl.service.getSearchResultTopic(
                token = token,
                topic = searchWord.value!!
            ).customEnqueue(
                    onSuccess = {
                            searchResultTopicList.value = it
                    },
                    onError = {
                        Log.e("WordActivity", "callSearchTopic() error : ${it.code()}")
                    }
                )
        }
    }

    // 글 내용 검색
    fun callSearchContents(){
        viewModelScope.launch {
            SangleServiceImpl.service.getSearchResultContents(
                token = token,
                topic = searchWord.value!!
            ).customEnqueue(
                onSuccess = {
                    searchResultTopicList.value = it
                },
                onError = {
                    Log.e("WordActivity", "callSearchContents() error : ${it.code()}")
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}