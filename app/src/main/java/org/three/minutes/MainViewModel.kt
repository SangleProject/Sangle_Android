package org.three.minutes


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel():ViewModel(){

    var progress : MutableLiveData<Int> = MutableLiveData()

    init {
        progress.value = 0
    }

    fun increase(){
        progress.value = progress.value?.plus(1)
    }

    fun decrease(){
        progress.value = progress.value?.minus(1)
    }
}