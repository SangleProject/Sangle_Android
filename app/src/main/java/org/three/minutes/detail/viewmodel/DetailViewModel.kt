package org.three.minutes.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.three.minutes.home.data.FeedData
import org.three.minutes.home.data.ResponseFameData

class DetailViewModel(data: ResponseFameData) : ViewModel() {
    val feedData = data
    val wordLength = feedData.postWrite.length
}