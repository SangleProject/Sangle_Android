package org.three.minutes.word.data

data class ResponseSearchTopicData(
    val postIdx: Int,
    val profileImg: String,
    val nickName: String,
    val topic: String,
    val postWrite: String,
    val date: String,
    val time: String,
    val day: String,
    val likes: Int,
    val info : String,
    val liked : Boolean
)