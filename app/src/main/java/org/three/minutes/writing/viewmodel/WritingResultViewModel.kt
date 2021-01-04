package org.three.minutes.writing.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.formatDate
import org.three.minutes.writing.data.BadgeData
import org.three.minutes.writing.data.RequestWritingData
import java.util.*

class WritingResultViewModel : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    var token = ""
    var contents = MutableLiveData("")
    var topic = MutableLiveData("")
    var contentsCount = MutableLiveData(0)
    var writingDate = MutableLiveData("null")
    var postIdx = -1
    var badgeList = MutableLiveData<MutableList<BadgeData>>()

    var isDelete = MutableLiveData(false)

    // 글 쓰기 완료 시 기점으로 현재 시간 가져오기
    fun getCurrentTime() {
        viewModelScope.launch {
            // Gregorian : 기존 Calendar 보다 쉽고 윤년 파악 가능
            val calendar = GregorianCalendar()
            writingDate.postValue(calendar.time.formatDate())
        }
    }

    // 글쓰기 결과 화면 진입 시 글 저장 api 호출
    // 뱃지가 있으면 뱃지 보여주기
    fun postWriting() {
        SangleServiceImpl.service.postWrite(
            token = token,
            RequestWritingData(topic = topic.value!!, postWrite = contents.value!!)
        ).customEnqueue(
            onSuccess = {
                postIdx = it.postIdx
                if (it.badge.isNotEmpty()) {
                    badgeList.value = it.badge.toMutableList()
                }
            },
            onError = {
                Log.e("WritingResultActivity", "postWriting error : ${it.code()}")
            }
        )
    }

    fun deleteWriting() {
        SangleServiceImpl.service.deleteWriting(token, postIdx)
            .customEnqueue(
                onSuccess = {
                    isDelete.value = true
                },
                onError = {
                    Log.e("WritingResultActivity", "Delete Error ${it.code()}")
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}