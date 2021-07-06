package org.three.minutes.notice.data

import com.google.gson.annotations.SerializedName

data class ResponseNoticeData(
    @SerializedName("createdAt")
    val date : String,
    val title : String,
    @SerializedName("content")
    val contents : String
)