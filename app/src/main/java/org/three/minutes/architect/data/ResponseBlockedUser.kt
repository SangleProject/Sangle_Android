package org.three.minutes.architect.data

import com.google.gson.annotations.SerializedName

data class ResponseBlockedUser(
    @SerializedName("blockedIdx")
    val blockedIdx: Int,
    @SerializedName("nickName")
    val nickName: String
)