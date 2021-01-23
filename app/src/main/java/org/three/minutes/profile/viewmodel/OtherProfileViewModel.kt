package org.three.minutes.profile.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.ThreeApplication
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.profile.data.ResponseDiffProfileData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue

class OtherProfileViewModel : ViewModel() {
    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""
    var userName = MutableLiveData("")

    var diffInfo = MutableLiveData<ResponseDiffProfileData>()
    var diffFeedList = MutableLiveData<List<ResponseOtherWritingData>>(listOf())

    var diffFeedRecent = MutableLiveData<List<ResponseOtherWritingData>>(listOf())
    var diffFeedPopular = MutableLiveData<List<ResponseOtherWritingData>>(listOf())

    var filter = MutableLiveData("최신순")


    fun callDiffInfo(){
        viewModelScope.launch {
            SangleServiceImpl.service.getDiffProfileInfo(token = token, nickName = userName.value ?: "")
                .customEnqueue(
                    onSuccess = {
                        diffInfo.value = it
                    },
                    onError = {
                        Log.e("OtherProfileActivity","fun callDiffInfo() ERROR : ${it.code()}")
                    }
                )
        }
    }

    fun callDiffFeedRecent(){
        viewModelScope.launch {
            SangleServiceImpl.service.getDiffFeedRecent(token = token, nickName = userName.value ?: "")
                .customEnqueue(
                    onSuccess = {
                        diffFeedRecent.value = it
                        setRcvRecent()
                    },
                    onError = {
                        Log.e("OtherProfileActivity","fun callDiffFeedRecent() ERROR : ${it.code()}")

                    }
                )
        }
    }

    fun callDiffFeedPopular(){
        viewModelScope.launch {
            SangleServiceImpl.service.getDiffFeedRecent(token = token, nickName = userName.value ?: "")
                .customEnqueue(
                    onSuccess = {
                                diffFeedPopular.value = it
                    },
                    onError = {
                        Log.e("OtherProfileActivity","fun callDiffFeedRecent() ERROR : ${it.code()}")

                    }
                )
        }
    }

    fun setRcvRecent(){
        diffFeedList.value = diffFeedRecent.value
    }
    fun setRcvPopular(){
        diffFeedList.value = diffFeedPopular.value
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}