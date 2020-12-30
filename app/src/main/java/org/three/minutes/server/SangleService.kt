package org.three.minutes.server

import org.three.minutes.login.data.RequestGoogleLoginData
import org.three.minutes.login.data.ResponseGoogleLoginData
import org.three.minutes.signup.data.RequestCheckEmailData
import org.three.minutes.signup.data.RequestGoogleSignUpData
import org.three.minutes.signup.data.ResponseCheckEmailData
import org.three.minutes.signup.data.ResponseGoogleSignUpData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface SangleService {
    // 구글 로그인 검증
    @POST("/users/google")
    fun postLoginGoogle(
        @Body body : RequestGoogleLoginData
    ) : Call<ResponseGoogleLoginData>

    // 구글로 회원가입시 추가 정보 입력
    @PUT("/users/social")
    fun putGoogleSignUp(
        @Body body : RequestGoogleSignUpData
    ) : Call<ResponseGoogleSignUpData>

    // 이메일 중복 체크 api
    @POST("/users/checkEmail")
    fun postCheckEmail(
        @Body body : RequestCheckEmailData
    ) : Call<ResponseCheckEmailData>
}