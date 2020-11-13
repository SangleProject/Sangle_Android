package org.three.minutes.word.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.three.minutes.word.data.PastWritingData
import org.three.minutes.word.data.SearchWritingData
import org.three.minutes.word.data.TodayWordData

class WordViewModel () : ViewModel(){

    var todayWordList = mutableListOf<TodayWordData>()
    var pastWritingList = mutableListOf<PastWritingData>()

    // 라디오 버튼 체크
    var allCheck = MutableLiveData(true)
    var doneCheck = MutableLiveData(false)
    var notCheck = MutableLiveData(false)

    // 검색 관련 데이터 모음
    var searchWord = MutableLiveData("")
    var filter = MutableLiveData("최신순")
    var searchList = listOf<SearchWritingData>()
}