package org.three.minutes.detail.data

data class ResponseMyWritingData(
    val postIdx : Int,
    val nickName : String,
    val myNickName : String,
    val topic : String,
    val postWrite : String,
    val date : String,
    val time : String,
    val day : String,
    val likes : Int,
    val liked : Boolean,
    val modified : Boolean,
    var open : Boolean,
    val isBan: String
)