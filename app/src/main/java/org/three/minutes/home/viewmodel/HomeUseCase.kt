package org.three.minutes.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue

class HomeUseCase() {
    fun goToWriting(token : String, topic : MutableLiveData<String>){
        SangleServiceImpl.service.getTodayTopic(token)
            .customEnqueue(
                onSuccess = { topics ->
                    for (data in topics){
                        if (data.used){
                            continue
                        }
                        else{
                            topic.value = data.topic
                            break
                        }
                    }
                },
                onError = {
                    if (it.code() == 401){
                        // do Change Token
                        Log.e("HomeUseCase", "Change Token for goToWriting()")
                    }
                }
            )
    }
}