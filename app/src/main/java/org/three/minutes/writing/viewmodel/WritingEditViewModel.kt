package org.three.minutes.writing.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.three.minutes.ThreeApplication
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import org.three.minutes.writing.data.RequestWritingData

class WritingEditViewModel : ViewModel() {

    var callToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()


    var token = MutableLiveData("")
    var topic = MutableLiveData("")
    var contents = MutableLiveData("")
    var contentsCount = MutableLiveData(0)
    var postIdx = -1

    var isEdited = MutableLiveData(false)

    fun callEdit(){
        SangleServiceImpl.service.postEdit(
            token = token.value!!,
            postIdx = this.postIdx,
            body = RequestWritingData(topic = topic.value!!, postWrite = contents.value!!)
        ).customEnqueue(
            onSuccess = {
                isEdited.value = true
            },
            onError = {
                Log.e("WritingEditActivity", "${it.code()}")
            }
        )
    }
}