package org.three.minutes.signup.data

import com.google.gson.annotations.SerializedName

data class ResponseCheckEmailData(
    @SerializedName("isAvailable")
    val isCheck : Boolean
)