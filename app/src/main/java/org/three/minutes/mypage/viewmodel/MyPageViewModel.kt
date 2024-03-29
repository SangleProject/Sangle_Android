package org.three.minutes.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.ThreeApplication
import org.three.minutes.detail.data.ResponseMyWritingData
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue

class MyPageViewModel : ViewModel() {
    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""

    var filter = MutableLiveData("최신순")
    var scrapFilter = MutableLiveData("최신순")
    var myId = MutableLiveData("")
    var myIntro = MutableLiveData("")
    var myImg = MutableLiveData("")

    // My 서랍 페이지 내가 쓴 글 데이터, 담은 글 데이터
    var myPostList = MutableLiveData<List<ResponseMyWritingData>>(listOf())
    var myScrapList = MutableLiveData<List<ResponseOtherWritingData>>(listOf())

    var myPostListRecent = MutableLiveData<List<ResponseMyWritingData>>(listOf())
    var myPostListPopular = MutableLiveData<List<ResponseMyWritingData>>(listOf())
    var myScrapListRecent = MutableLiveData<List<ResponseOtherWritingData>>(listOf())
    var myScrapListPopular = MutableLiveData<List<ResponseOtherWritingData>>(listOf())

    // My 서랍에서 보여질 데이터 통신
    fun callMyData() {
        viewModelScope.launch {
            SangleServiceImpl.service.getMyPostRecent(token)
                .customEnqueue(
                    onSuccess = {
                        myPostListRecent.value = it
                        if (filter.value == "최신순") {
                            setMyPostRecent()
                        }
                    },
                    onError = {
                        Log.e("HomeActivity", "fun myPostRecent() error : ${it.code()}")
                    }
                )
            SangleServiceImpl.service.getMyPostPopular(token)
                .customEnqueue(
                    onSuccess = {
                        myPostListPopular.value = it
                        if (filter.value == "인기순") {
                            setMyPostPopular()
                        }
                    },
                    onError = {
                        Log.e("HomeActivity", "fun myPostPopular() error : ${it.code()}")
                    }
                )

            SangleServiceImpl.service.getMyScrapRecent(token)
                .customEnqueue(
                    onSuccess = {
                        myScrapListRecent.value = it
                        if (scrapFilter.value == "최신순") {
                            setMyScrapRecent()
                        }
                    },
                    onError = {
                        Log.e("HomeActivity", "fun myScrapRecent() error : ${it.code()}")
                    }
                )

            SangleServiceImpl.service.getMyScrapPopular(token)
                .customEnqueue(
                    onSuccess = {
                        myScrapListPopular.value = it
                        if (scrapFilter.value == "인기순") {
                            setMyScrapPopular()
                        }
                    },
                    onError = {
                        Log.e("HomeActivity", "fun myScrapRecent() error : ${it.code()}")
                    }
                )
        }
    }

    fun callMyInfo() {
        viewModelScope.launch {
            SangleServiceImpl.service.getProfile(
                token
            ).customEnqueue(
                onSuccess = {
                    myId.value = it.nickName
                    myIntro.value = it.info
                    myImg.value = it.profileImg
                },
                onError = {
                    Log.e("HomeActivity", "fun callMyInfo() error : ${it.code()}")
                }
            )
        }
    }

    fun setMyPostRecent() {
        myPostList.value = myPostListRecent.value
    }

    fun setMyPostPopular() {
        myPostList.value = myPostListPopular.value
    }

    fun setMyScrapRecent() {
        myScrapList.value = myScrapListRecent.value
    }

    fun setMyScrapPopular() {
        myScrapList.value = myScrapListPopular.value
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}