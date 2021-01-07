package org.three.minutes.detail.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.three.minutes.ThreeApplication
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.formatCount

class DetailOtherViewModel : ViewModel() {

    val getToken= ThreeApplication.getInstance().getDataStore().token.asLiveData()

    var token = ""
    var postIdx  = -1

    var detailData = MutableLiveData<ResponseOtherWritingData>()
    var postLength = MutableLiveData(0)
    var likeCount = MutableLiveData("")
    // 날짜 붙여서 표시
    var date = MutableLiveData("")

    fun callOtherDetailData(){
        SangleServiceImpl.service.getOtherDetailWriting(token, postIdx)
            .customEnqueue(
                onSuccess = {
                    detailData.value = it
                    date.value = "${it.date} (${it.day}) ${it.time}"
                    postLength.value = it.postWrite.length
                    likeCount.value = it.likes.formatCount()
                },
                onError = {
                    Log.e("DetailActivity", "callOtherDetailData() error : ${it.code()}")
                }
            )
    }
}