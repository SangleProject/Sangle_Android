package org.three.minutes.writing.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class WritingViewModel() : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)


    var timerCount= MutableLiveData(35)

    fun timerThreeMin() {
        viewModelScope.launch {
                startTimer()
        }
    }

    private suspend fun startTimer() {
        for (i in 5 downTo 0) {
            timerCount.postValue(i)
            delay(1000)
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}
