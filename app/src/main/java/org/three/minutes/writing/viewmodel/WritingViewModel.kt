package org.three.minutes.writing.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class WritingViewModel : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)



    var timerCount= MutableLiveData(180)
    var writingCount = MutableLiveData(0)
    // 제목 글감
    var topic = MutableLiveData("")


    fun timerThreeMin() {
        viewModelScope.launch {
                startTimer()
        }
    }

    private suspend fun startTimer() {
        for (i in 180 downTo 0) {

            timerCount.postValue(i)
            delay(1000)
        }
    }

//    fun stopCount(){
//        job.cancel()
//    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}
