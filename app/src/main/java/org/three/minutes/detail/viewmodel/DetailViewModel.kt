package org.three.minutes.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.ThreeApplication
import org.three.minutes.detail.data.ResponseMyWritingData
import org.three.minutes.home.data.FeedData
import org.three.minutes.home.data.ResponseFameData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.formatCount

class DetailViewModel : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()

    var token = ""
    var postIdx = -1
    var postLength = MutableLiveData(0)
    var detailData = MutableLiveData<ResponseMyWritingData>()
    var likeCount = MutableLiveData("")

    // 날짜 붙여서 표시
    var date = MutableLiveData("")

    fun callMyDetailData() {
        viewModelScope.launch {
            SangleServiceImpl.service.getMyDetailWriting(token, postIdx)
                .customEnqueue(
                    onSuccess = {
                        detailData.value = it
                        date.value = "${it.date} (${it.day}) ${it.time}"
                        postLength.value = it.postWrite.length
                        likeCount.value = it.likes.formatCount()
                    },
                    onError = {
                        Log.e("DetailMyActivity", "calMyDetailData() error : ${it.code()}")
                    }
                )
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}