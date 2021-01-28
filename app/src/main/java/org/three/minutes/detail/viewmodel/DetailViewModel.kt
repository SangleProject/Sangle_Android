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
import org.three.minutes.detail.data.ResponseMyWritingData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.formatCount
import org.three.minutes.util.showToast

class DetailViewModel : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var isDelete = MutableLiveData(false)

    var token = ""
    var postIdx = -1
    var postLength = MutableLiveData(0)
    var detailData = MutableLiveData<ResponseMyWritingData>()
    var likeCount = MutableLiveData("")
    var likeCountInteger = 0

    // 맨 처음 액티비티 진입시 통신 값이 true면 체크 박스 값이 바뀌면서 통신이 한 번 더 일어남
    // 그러한 작업을 방지하기 위해 변수를 만들어 놓고 사용
    var likeFirst = true

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
                        likeCountInteger = it.likes
                        likeCount.value = likeCountInteger.formatCount()

                        setLiked()
                    },
                    onError = {
                        Log.e("DetailMyActivity", "calMyDetailData() error : ${it.code()}")
                    }
                )
        }
    }

    private fun setLiked() {
        if (!detailData.value!!.liked){
            likeFirst = false
        }
    }

    fun callLike(context : Context) {
        viewModelScope.launch {
            SangleServiceImpl.service.postLike(token, postIdx)
                .customEnqueue(
                    onSuccess = {
                        likeCountInteger += 1
                        likeCount.value = likeCountInteger.formatCount()
                        context.showToast("좋은 글에 좋아요를 눌렀어요!")
                    },
                    onError = {
                        Log.e("DetailMyActivity", "callLike() error : ${it.code()}")
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
                        context.showToast("좋아요를 취소했어요.")
                    },
                    onError = {
                        Log.e("DetailMyActivity", "callLike() error : ${it.code()}")
                    }
                )
        }
    }

    fun callDeleteData(){
        viewModelScope.launch {
            SangleServiceImpl.service.deleteWriting(
                token = token, postIdx = postIdx)
                .customEnqueue(
                    onSuccess = {
                        isDelete.value = true
                    },
                    onError = {
                        Log.e("DetailMyActivity", "calDeleteData() error : ${it.code()}")

                    }
                )
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}