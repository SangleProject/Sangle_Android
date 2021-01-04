package org.three.minutes.writing.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.three.minutes.ThreeApplication
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import org.three.minutes.writing.data.BadgeData
import org.three.minutes.writing.data.RequestWritingData

class WritingEditViewModel : ViewModel() {

    var token = ""
    var topic = MutableLiveData("")
    var contents = MutableLiveData("")
    var contentsCount = MutableLiveData(0)
    var postIdx = -1

    var isEdited = MutableLiveData(false)

    var badgeList = MutableLiveData<MutableList<BadgeData>>()

    fun callEdit(){
        SangleServiceImpl.service.postEdit(
            token = token,
            postIdx = this.postIdx,
            body = RequestWritingData(topic = topic.value!!, postWrite = contents.value!!)
        ).customEnqueue(
            onSuccess = {
                isEdited.value = true
                if (it.badge.isNotEmpty()){
                    badgeList.value = it.badge.toMutableList()
                }
            },
            onError = {
                Log.e("WritingEditActivity", "${it.code()}")
            }
        )
    }
}