package org.three.minutes.word.viewmodel

import androidx.lifecycle.ViewModel
import org.three.minutes.util.changeEmoji

class WordViewModel () : ViewModel(){

    val pageEmoji = 0x1F4DD.changeEmoji()
    val wordTitle = "오늘의 글감 $pageEmoji"

}