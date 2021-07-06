package org.three.minutes.detail.data

data class ResponseOtherWritingData(
    val postIdx : Int,
    val profileImg : String,
    val nickName : String,
    val myNickName : String,
    val topic : String,
    val postWrite : String,
    val date : String,
    val time : String,
    val day : String,
    val likes : Int,
    val modified : Boolean,
    val scrap : Boolean,
    val liked : Boolean,
    val isBan: String
)
