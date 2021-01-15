package org.three.minutes.detail.viewmodel


import android.content.Context
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
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.formatCount
import org.three.minutes.util.showToast

class DetailOtherViewModel : ViewModel() {
    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()

    var token = ""
    var postIdx = -1

    var detailData = MutableLiveData<ResponseOtherWritingData>()
    var postLength = MutableLiveData(0)
    var likeCount = MutableLiveData("")
    var likeCountInteger = 0

    // 날짜 붙여서 표시
    var date = MutableLiveData("")

    fun callOtherDetailData() {
        viewModelScope.launch {
            SangleServiceImpl.service.getOtherDetailWriting(token, postIdx)
                .customEnqueue(
                    onSuccess = {
                        detailData.value = it
                        likeCountInteger = it.likes
                        date.value = "${it.date} (${it.day}) ${it.time}"
                        postLength.value = it.postWrite.length
                        likeCount.value = likeCountInteger.formatCount()
                    },
                    onError = {
                        Log.e("DetailActivity", "callOtherDetailData() error : ${it.code()}")
                    }
                )
        }
    }

    fun callLike(context : Context) {
        viewModelScope.launch {
            SangleServiceImpl.service.postLike(token, postIdx)
                .customEnqueue(
                    onSuccess = {
                        likeCountInteger += 1
                        likeCount.value = likeCountInteger.formatCount()
                        context.showToast("좋은 글을 스크랩했어요!")
                    },
                    onError = {
                        Log.e("DetailActivity", "callLike() error : ${it.code()}")
                    }
                )
        }
    }

    fun callUnLike(context: Context){
        viewModelScope.launch {
            SangleServiceImpl.service.deleteUnlike(token = token, postIdx = postIdx)
                .customEnqueue(
                    onSuccess = {
                        likeCountInteger -= 1
                        likeCount.value = likeCountInteger.formatCount()
                        context.showToast("스크랩을 취소했어요!")
                    },
                    onError = {
                        Log.e("DetailActivity", "callLike() error : ${it.code()}")
                    }
                )
        }
    }

    fun callScrap(context: Context){
        viewModelScope.launch {
            SangleServiceImpl.service.postScrap(token = token, postIdx = postIdx)
                .customEnqueue(
                    onSuccess = {

                    },
                    onError = {

                    }
                )
        }
    }

    fun callUnScrap(context: Context){
        viewModelScope.launch {
            SangleServiceImpl.service.deleteUnScrap(token = token, postIdx = postIdx)
                .customEnqueue(
                    onSuccess = {

                    },
                    onError = {
                        
                    }
                )
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}