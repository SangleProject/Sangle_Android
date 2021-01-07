package org.three.minutes.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.three.minutes.home.data.FeedData
import org.three.minutes.home.data.ResponseFameData
import java.lang.IllegalArgumentException

class DetailViewModelFactory(val data: ResponseFameData) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            DetailViewModel(data = this.data) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}