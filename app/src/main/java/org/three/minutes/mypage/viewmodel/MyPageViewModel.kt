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
import org.three.minutes.mypage.data.MyWritingData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue

class MyPageViewModel : ViewModel(){
    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""

    var filter = MutableLiveData("최신순")
    var myId = MutableLiveData("머리가말랑말랑")
    var myIntro = MutableLiveData("소소한 일상을 나답게 살아가고자 글을 씁니다!")
    var myWritingList = mutableListOf<MyWritingData>()

    // My 서랍 페이지 내가 쓴 글 데이터, 담은 글 데이터
    var myPostList = MutableLiveData<List<ResponseMyWritingData>>(listOf())
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
                        if (filter.value == "최신순"){
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
                        if (filter.value == "인기순"){
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

    fun setMyPostRecent(){
        myPostList.value = myPostListRecent.value
    }

    fun setMyPostPopular(){
        myPostList.value = myPostListPopular.value
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}