package org.three.minutes.word.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.three.minutes.ThreeApplication
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import org.three.minutes.word.data.ResponseLastTopicData

class WordAllTopicViewModel :ViewModel() {

    var getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""

    var lastTopicList = MutableLiveData<List<ResponseLastTopicData>>(listOf())

    fun getAllTopic(){

        viewModelScope.launch {
            SangleServiceImpl.service.getAllTopic(token)
                .customEnqueue(
                    onSuccess = {
                                lastTopicList.value = it
                    },
                    onError = {
                        Log.e("WordAllTopic","${it.code()}")
                    }
                )
        }
    }
}