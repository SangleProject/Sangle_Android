package org.three.minutes.writing.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.util.formatDate
import java.util.*

class WritingResultViewModel : ViewModel(){

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    var contents = MutableLiveData("")
    var topic = MutableLiveData("")

    var contentsCount = MutableLiveData(0)

    var writingDate = MutableLiveData("null")

    fun getCurrentTime(){
        viewModelScope.launch {
            // Gregorian : 기존 Calendar보다 쉽고 윤년 파악 가능
            val calendar = GregorianCalendar()
            writingDate.postValue(calendar.time.formatDate())
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}