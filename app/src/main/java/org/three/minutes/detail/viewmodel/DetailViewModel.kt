package org.three.minutes.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.three.minutes.home.data.FeedData

class DetailViewModel(data: FeedData) : ViewModel() {
    val feedData = data
    val wordLength = feedData.contents.length
}