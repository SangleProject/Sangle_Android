package org.three.minutes.server

import org.three.minutes.home.data.ResponseMainInfoData
import org.three.minutes.home.data.ResponseTodayTopicData
import org.three.minutes.login.data.RequestGoogleLoginData
import org.three.minutes.login.data.RequestLoginData
import org.three.minutes.login.data.ResponseGoogleLoginData
import org.three.minutes.signup.data.*
import org.three.minutes.login.data.ResponseLoginData
import org.three.minutes.signup.data.RequestGoogleSignUpData
import org.three.minutes.writing.data.RequestWritingData
import org.three.minutes.writing.data.ResponseWritingData
import org.three.minutes.writing.data.ResponseWritingEditData
import retrofit2.Call
import retrofit2.http.*

interface SangleService {
    //토큰 만료 api
    @GET("/users/refresh")
    fun getToken(
        @Header("refresh") refresh : String
    ) : Call<ResponseSignUpData>

    // 구글 로그인 검증
    @POST("/users/google")
    fun postLoginGoogle(
        @Body body : RequestGoogleLoginData
    ) : Call<ResponseGoogleLoginData>

    // 구글로 회원가입시 추가 정보 입력
    @PUT("/users/social")
    fun putGoogleSignUp(
        @Body body : RequestGoogleSignUpData
    ) : Call<ResponseSignUpData>

    // 이메일 중복 체크 api
    @POST("/users/checkEmail")
    fun postCheckEmail(
        @Body body : RequestCheckEmailData
    ) : Call<ResponseCheckData>

    // 닉네임 중복 체크 api
    @POST("/users/checkNickName")
    fun postCheckNickName(
        @Body body : RequestCheckNickNameData
    ) : Call<ResponseCheckData>

    // 일반 회원가입 api
    @POST("/users/signup")
    fun postSignUp(
        @Body body : RequestSignUpData
    ) : Call<ResponseSignUpData>

    // 일반 로그인 API
    @POST("/users/signin")
    fun postLogIn(
        @Body body : RequestLoginData
    ) : Call<ResponseLoginData>

    // 메인 정보 불러오기
    @GET("/main/info")
    fun getMainInfo(
        @Header("token") token : String
    ) : Call<ResponseMainInfoData>

    //오늘의 글감 가져오기
    @GET("/topic/today")
    fun getTodayTopic(
        @Header("token") token : String
    ) : Call<List<ResponseTodayTopicData>>

    // 글쓰기 저장
    @POST("/posts/write")
    fun postWrite(
        @Header("token") token : String,
        @Body body : RequestWritingData
    ) : Call<ResponseWritingData>

    // 글쓰기 수정
    @POST("/posts/update/{postIdx}")
    fun postEdit(
        @Header("token") token : String,
        @Path("postIdx") postIdx : Int,
        @Body body : RequestWritingData
    ) : Call<ResponseWritingEditData>

}