package org.three.minutes.signup.data

import com.google.gson.annotations.SerializedName

data class ResponseCheckData(
    @SerializedName("isAvailable")
    val isCheck : Boolean
)