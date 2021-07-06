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
import org.three.minutes.word.data.*

class WordViewModel : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    var getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""
    var isKeyboardShow = false

    var todayTopicList = MutableLiveData<List<ResponseTodayTopicData>>(listOf())
    var lastTopicList = MutableLiveData<List<ResponseLastTopicData>>(listOf())

    // 지난 글감 리스트 데이터 모음
    var lastDetailTopic = MutableLiveData("")
    var lastTopicDetailList = MutableLiveData<List<ResponsePastSearchData>>(listOf())
    var lastTopicDetailCount = MutableLiveData(0)
    var lastTopicOk = MutableLiveData(false)
    var filter = MutableLiveData("최신순")

    // 검색 데이터
    var isFilterTopic = MutableLiveData(true)
    var isFilterContents = MutableLiveData(false)
    var isFilterUser = MutableLiveData(false)
    var searchWord = MutableLiveData("")
    var searchResultList = MutableLiveData<List<ResponseSearchTopicData>>(listOf())
    var searchResultTopicList = MutableLiveData<List<ResponseSearchTopicData>>(listOf())
    var searchResultContentList = MutableLiveData<List<ResponseSearchTopicData>>(listOf())
    var searchUserList = MutableLiveData<List<ResponseUserListData>>(listOf())

    //내가 쓴 글인지 아닌지 여부 판단 데이터
    var isWritten = MutableLiveData(false)

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
                    callWritten(pastTopic)
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
                    callWritten(pastTopic)
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
                    callWritten(searchWord.value!!)
                    if (isFilterTopic.value!!) {
                        changeTopicList()
                    }
                },
                onError = {
                    Log.e("WordActivity", "callSearchTopic() error : ${it.code()}")
                }
            )
        }
    }

    // 내가 작성한 글인지 아닌지 판단하는 api 호출 함수
    private fun callWritten(topic : String) {
        viewModelScope.launch {
            SangleServiceImpl.service.postWritten(
                token = token,
                body = RequestWrittenData(topic = topic)
            ).customEnqueue(
                onSuccess = {
                    isWritten.value = it.written
//                    Log.e("WordActivity", "Server written : ${it.written}")
//                    Log.e("WordActivity", "$topic isWritten : ${isWritten.value}")
                },
                onError = {
                    Log.e("WordActivity", "callWritten() error : ${it.code()}")

                }
            )
        }
    }

    fun changeTopicList() {
        searchResultList.value = searchResultTopicList.value
    }

    fun changeContentsList() {
        searchResultList.value = searchResultContentList.value
    }

    // 글 내용 검색
    fun callSearchContents() {
        viewModelScope.launch {
            SangleServiceImpl.service.getSearchResultContents(
                token = token,
                topic = searchWord.value!!
            ).customEnqueue(
                onSuccess = {
                    searchResultContentList.value = it
                    callWritten(searchWord.value!!)
                    if (isFilterContents.value!!) {
                        changeContentsList()
                    }
                },
                onError = {
                    Log.e("WordActivity", "callSearchContents() error : ${it.code()}")
                }
            )
        }
    }

    // 유저 검색
    fun callSearchUser() {
        viewModelScope.launch {
            SangleServiceImpl.service.getSearchUser(
                token = token,
                user = searchWord.value!!
            ).customEnqueue(
                onSuccess = {
                    searchUserList.value = it
                },
                onError = {
                    Log.e("WordActivity", "callSearchUser() error : ${it.code()}")
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}