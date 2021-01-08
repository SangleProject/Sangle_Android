package org.three.minutes.server

import okhttp3.ResponseBody
import org.three.minutes.detail.data.ResponseMyWritingData
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.home.data.ResponseFameData
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
    @PUT("/posts/update/{postIdx}")
    fun postEdit(
        @Header("token") token : String,
        @Path("postIdx") postIdx : Int,
        @Body body : RequestWritingData
    ) : Call<ResponseWritingEditData>

    //글쓰기 삭제
    @Headers("Content-Type:application/json")
    @DELETE("/posts/delete/{postIdx}")
    fun deleteWriting(
        @Header("token") token : String,
        @Path("postIdx") postIdx: Int
    ):Call<ResponseBody>

    //명예의 전당 데이터 가져오기
    @GET("/posts/popularity")
    fun getFameData(
        @Header("token") token : String
    ) : Call<List<ResponseFameData>>

    //내가 쓴 게시글 자세히 보기
    @GET("/posts/detail/{postIdx}")
    fun getMyDetailWriting(
        @Header("token") token: String,
        @Path("postIdx") postIdx: Int
    ) : Call<ResponseMyWritingData>

    //남이 쓴 게시글 자세히 보기
    @GET("/posts/detailDiff/{postIdx}")
    fun getOtherDetailWriting(
        @Header("token") token : String,
        @Path("postIdx") postIdx: Int
    ) : Call<ResponseOtherWritingData>
}