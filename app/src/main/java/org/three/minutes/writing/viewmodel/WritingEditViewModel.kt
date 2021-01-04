package org.three.minutes.writing.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WritingEditViewModel : ViewModel() {

    var topic = MutableLiveData("")
    var contents = MutableLiveData("")
    var contentsCount = MutableLiveData(0)
}