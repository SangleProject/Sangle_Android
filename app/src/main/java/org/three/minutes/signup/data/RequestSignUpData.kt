package org.three.minutes.signup.data

import com.google.gson.annotations.SerializedName

data class RequestSignUpData(
    val email : String,
    val password : String,
    val gender : String,
    val birth : String,
    val nickName : String,
    @SerializedName("device_token")
    val deviceToken : String
)