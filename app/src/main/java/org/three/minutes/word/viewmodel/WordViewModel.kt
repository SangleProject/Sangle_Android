package org.three.minutes.word.viewmodel


import androidx.lifecycle.ViewModel
import org.three.minutes.word.data.TodayWordData

class WordViewModel () : ViewModel(){

    var todayWordList = mutableListOf<TodayWordData>()

}