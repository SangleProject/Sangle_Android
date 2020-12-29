package org.three.minutes.signup.data

import com.google.gson.annotations.SerializedName

data class RequestGoogleSignUpData(
    val email : String,
    val gender : String,
    val birth : String,
    val nickName : String,
    @SerializedName("device_token")
    val deviceToken : String
)
