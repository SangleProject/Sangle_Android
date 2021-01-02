package org.three.minutes.writing.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WritingResultViewModel : ViewModel(){

    var contents = MutableLiveData("")
    var topic = MutableLiveData("")

    var contentsCount = MutableLiveData(0)
}