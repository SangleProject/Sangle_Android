package org.three.minutes.login.data

data class ResponseGoogleLoginData(
    val user : Boolean,
    val token : String,
    val refresh : String,
    val status : Boolean
)