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
    var dateFormat = MutableLiveData("")
    var postLength = MutableLiveData(0)
    var likeCount = MutableLiveData("")
    var likeCountInteger = 0

    // 신고하기 창에서 기타 탭을 눌렀을 경우 나오는 EditText 값
    val reportEtc = MutableLiveData("")
    // 신고하기 종류 string 값
    var reportContents = ""

    // 맨 처음 액티비티 진입시 통신 값이 true면 체크 박스 값이 바뀌면서 통신이 한 번 더 일어남
    // 그러한 작업을 방지하기 위해 변수를 만들어 놓고 사용
    var likeFirst = true
    var scrapFirst = true

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
                        setDate(it)
                        checkChangeCheckBox()
                    },
                    onError = {
                        Log.e("DetailActivity", "callOtherDetailData() error : ${it.code()}")
                    }
                )
        }
    }

    private fun checkChangeCheckBox(){
        if (!detailData.value!!.liked){
            likeFirst = false
        }

        if (!detailData.value!!.scrap){
            scrapFirst = false
        }
    }

    private fun setDate(data : ResponseOtherWritingData) {
        val formatDate = "${data.date} (${data.day}) ${data.time}"
        dateFormat.value = formatDate
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
                        context.showToast("좋아요를 취소했어요.")
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
                        context.showToast("좋은 글을 스크랩 했어요!")
                    },
                    onError = {
                        Log.e("DetailActivity", "callScrap() error : ${it.code()}")
                    }
                )
        }
    }

    fun callUnScrap(context: Context){
        viewModelScope.launch {
            SangleServiceImpl.service.deleteUnScrap(token = token, postIdx = postIdx)
                .customEnqueue(
                    onSuccess = {
                        context.showToast("스크랩을 취소 했어요.")
                    },
                    onError = {
                        Log.e("DetailActivity", "callUnScrap() error : ${it.code()}")
                    }
                )
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}