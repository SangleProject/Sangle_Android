package org.three.minutes.notice.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.ThreeApplication
import org.three.minutes.notice.data.ResponseNoticeData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue

class NoticeViewModel : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val _token = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""

    var noticeList = MutableLiveData<List<ResponseNoticeData>>(listOf())

    fun callGetNotice(){
        viewModelScope.launch {
            SangleServiceImpl.service.getNoticeApi(token = token)
                .customEnqueue(
                    onSuccess = {
                        noticeList.value = it
                    },
                    onError = {
                        Log.e("NoticeViewActivity", "callGetNotice() error : ${it.code()}")
                    }
                )
        }
    }
}