package org.three.minutes.server

import org.three.minutes.login.data.RequestGoogleLoginData
import org.three.minutes.login.data.ResponseGoogleLoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SangleService {
    // 구글 로그인 검증
    @POST("/users/google")
    fun postLoginGoogle(
        @Body body : RequestGoogleLoginData
    ) : Call<ResponseGoogleLoginData>
}