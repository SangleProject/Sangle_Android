package org.three.minutes.server

import okhttp3.ResponseBody
import org.three.minutes.badge.data.ResponseBadgeData
import org.three.minutes.detail.data.ResponseMyWritingData
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.home.data.*
import org.three.minutes.login.data.RequestGoogleLoginData
import org.three.minutes.login.data.RequestLoginData
import org.three.minutes.login.data.ResponseGoogleLoginData
import org.three.minutes.signup.data.*
import org.three.minutes.login.data.ResponseLoginData
import org.three.minutes.mypage.data.ResponseMyInfoData
import org.three.minutes.profile.data.RequestProfileData
import org.three.minutes.profile.data.ResponseDiffProfileData
import org.three.minutes.signup.data.RequestGoogleSignUpData
import org.three.minutes.word.data.ResponseLastTopicData
import org.three.minutes.word.data.ResponsePastSearchData
import org.three.minutes.word.data.ResponseSearchTopicData
import org.three.minutes.writing.data.BadgeData
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

    //좋아요 설정
    @POST("/posts/likes/{postIdx}")
    fun postLike(
        @Header("token") token : String,
        @Path("postIdx") postIdx : Int
    ): Call<List<BadgeData>>

    // 좋아요 취소
    @Headers("Content-Type:application/json")
    @DELETE("/posts/unlikes/{postIdx}")
    fun deleteUnlike(
        @Header("token") token : String,
        @Path("postIdx") postIdx: Int
    ) : Call<ResponseBody>

    // 스크랩 하기
    @POST("/posts/scrap/{postIdx}")
    fun postScrap(
        @Header("token") token: String,
        @Path("postIdx") postIdx: Int
    ) : Call<List<BadgeData>>

    //스크랩 취소
    @Headers("Content-Type:application/json")
    @DELETE("/posts/unscrap/{postIdx}")
    fun deleteUnScrap(
        @Header("token") token : String,
        @Path("postIdx") postIdx: Int
    ) : Call<ResponseBody>

    // 지난 글감 (10일치) 가져오기
    @GET("/topic/lastTopic")
    fun getLastTopic(
        @Header("token") token : String
    ) : Call<List<ResponseLastTopicData>>

    // 지난 top 10 글감  상세 검색 api ( 최신순 = default )
    @GET("/posts/all")
    fun getTopicSearchRecent(
        @Header("token") token : String,
        @Query("topic") topic : String
    ) : Call<List<ResponsePastSearchData>>

    // 지난 top 10 글감  상세 글감 검색 api ( 인기순 )
    @GET("/posts/all")
    fun getTopicSearchPopular(
        @Header("token") token : String,
        @Query("topic") topic : String,
        @Query("filter") filter : String = "popular"
    ) : Call<List<ResponsePastSearchData>>

    // 검색 글감 api
    @GET("/main/searchTopic")
    fun getSearchResultTopic(
        @Header("token") token: String,
        @Query("topic") topic : String
    ) : Call<List<ResponseSearchTopicData>>

    // 검색 글 내용 api
    @GET("/main/searchPost")
    fun getSearchResultContents(
        @Header("token") token : String,
        @Query("topic") topic: String
    ) : Call<List<ResponseSearchTopicData>>

    // 검색 유저 api
    @GET("/main/searchUser")
    fun getSearchUser(
        @Header("token") token : String,
        @Query("user") user : String
    ) : Call<List<ResponseSearchTopicData>>

    // My 서랍 작성한 글 목록 ( 최신순 )
    @GET("/posts/myPost")
    fun getMyPostRecent(
        @Header("token") token : String
    ) : Call<List<ResponseMyWritingData>>

    // My 서랍 작성한 글 목록 ( 인기순 )
    @GET("/posts/myPost")
    fun getMyPostPopular(
        @Header("token") token : String,
        @Query("filter") filter : String = "popular"
    ) : Call<List<ResponseMyWritingData>>

    // My 서랍 담은 글 목록 ( 최신순 )
    @GET("/posts/scrap")
    fun getMyScrapRecent(
        @Header("token") token : String
    ) : Call<List<ResponseOtherWritingData>>

    // My 서랍 담은 글 목록 ( 인기순 )
    @GET("/posts/scrap")
    fun getMyScrapPopular(
        @Header("token") token : String,
        @Query("filter") filter : String = "popular"
    ) : Call<List<ResponseOtherWritingData>>

    // My 서랍 및 프로필 변경에서 사용 될 api
    @GET("/users/profile")
    fun getProfile(
        @Header("token") token: String
    ) : Call<ResponseMyInfoData>

    // 캘린더 정보 가져오기 api
    @GET("/cal/day/{date}")
    fun getCalendar(
        @Header("token") token : String,
        @Path("date") date : String
    ) : Call<List<ResponseCalendarData>>

    // 주별 달성률 가져오기 api
    @GET("/cal/week")
    fun getWeekComplete(
        @Header("token") token : String
    ) : Call<ResponseWeekCompleteData>

    // 프로필 수정 api
    @PUT("/users/update")
    fun putProfileChange(
        @Header("token") token : String,
        @Body body : RequestProfileData
    ) : Call<List<ResponseMainInfoData.Badge>>

    // 뱃지 리스트 가져오기 api
    @GET("/badgeList")
    fun getBadgeList(
        @Header("token") token : String
    ) : Call<List<ResponseBadgeData>>

    // 다른 유저 프로필 가져오기 api
    @GET("/main/diffProfile/{nickName}")
    fun getDiffProfileInfo(
        @Header("token") token : String,
        @Path("nickName") nickName : String
    ) : Call<ResponseDiffProfileData>

    // 다른 유저 피드 최신순 ( default )
    @GET("/main/diffFeed/{nickName}")
    fun getDiffFeedRecent(
        @Header("token") token : String,
        @Path("nickName") nickName : String
    ) : Call<List<ResponseOtherWritingData>>

    // 다른 유저 피드 인기순
    @GET("/main/diffFeed/{nickName}")
    fun getDiffFeedPopular(
        @Header("token") token : String,
        @Path("nickName") nickName : String,
        @Query("filter") filter : String = "popular"
    ) : Call<List<ResponseOtherWritingData>>
}