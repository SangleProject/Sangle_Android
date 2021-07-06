package org.three.minutes.detail.data

import com.google.gson.annotations.SerializedName

data class ResponseReport(
    @SerializedName("postIdx")
    val postIdx: Int,
    @SerializedName("userIdx")
    val userIdx: Int,
    @SerializedName("isBanned")
    val isBanned: Boolean,
    @SerializedName("adminMemo")
    val adminMemo: String
)
