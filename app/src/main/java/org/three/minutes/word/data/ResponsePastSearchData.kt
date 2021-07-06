package org.three.minutes.word.data

data class ResponsePastSearchData(
    val date: String,
    val day: String,
    val likes: Int,
    val liked : Boolean,
    val modified: String,
    val myNickName: String,
    val nickName: String,
    val postIdx: Int,
    val postWrite: String,
    val profileImg: String,
    val time: String,
    val topic: String
)