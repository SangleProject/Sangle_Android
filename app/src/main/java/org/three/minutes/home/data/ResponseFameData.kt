package org.three.minutes.home.data

import org.three.minutes.writing.data.BadgeData
import java.io.Serializable

data class ResponseFameData(
    val postIdx : Int,
    val nickName : String,
    val myNickName : String,
    val topic : String,
    val postWrite : String,
    val date : String,
    val time : String,
    val day : String,
    val likes : Int,
    val badge : List<BadgeData>
) : Serializable
