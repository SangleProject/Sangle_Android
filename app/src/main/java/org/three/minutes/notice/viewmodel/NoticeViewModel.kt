package org.three.minutes.notice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.three.minutes.ThreeApplication
import org.three.minutes.notice.data.ResponseNoticeData

class NoticeViewModel : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val _token = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""

    var noticeList = MutableLiveData<List<ResponseNoticeData>>(listOf())
}