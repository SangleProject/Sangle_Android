package org.three.minutes.badge.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.ThreeApplication
import org.three.minutes.badge.data.BadgeListData
import org.three.minutes.badge.data.ResponseBadgeData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue

class BadgeViewModel : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""
    //뱃지 리스트를 담아놓은 리스트
    var badgeList = mutableListOf<BadgeListData>()
    var responseBadgeList = MutableLiveData<List<ResponseBadgeData>>(listOf())

    fun callBadgeList(){
        viewModelScope.launch {
            SangleServiceImpl.service.getBadgeList(token)
                .customEnqueue(
                    onSuccess = {
                        responseBadgeList.value = it
                    },
                    onError = {
                        Log.e("BadgeActivity","fun callBadgeList() ERROR : ${it.code()}")
                    }
                )
        }
    }


    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}