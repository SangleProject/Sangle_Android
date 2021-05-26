package org.three.minutes.profile.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.profile.data.ProfileData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue

class ProfileViewModel : ViewModel() {
    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""

    var profileName = MutableLiveData("")
    /**
     * 2021.05.26 버그 수정
     * 프로필 업데이트 시 기존 닉네임 그대로 사용할 경우 중복체크 api에서 막히는 현상 발생
     * 기존 변경 닉네임을 저장한 값과 현재 바꾼 닉네임을 비교하여 둘이 동일하다면
     * 중복체크 api 호출 스킵
     */
    var lastProfileName = ""
    var introduce = MutableLiveData("")
    var introduceCount = MutableLiveData(0)
    var imgIndex = MutableLiveData(0)

    // 닉네임을 한글자라도 입력해야지만 저장 가능
    var isOk = false
    var isCalledProfile = MutableLiveData(false)


    val profileImgList = mutableListOf(
        ProfileData(0, R.drawable.profile1),
        ProfileData(1, R.drawable.profile2),
        ProfileData(2, R.drawable.profile3),
        ProfileData(3, R.drawable.profile4),
        ProfileData(4, R.drawable.profile5),
        ProfileData(5, R.drawable.profile6),
        ProfileData(6, R.drawable.profile7),
        ProfileData(7, R.drawable.profile8)
    )

    fun callMyInfo(){
        viewModelScope.launch {
            SangleServiceImpl.service.getProfile(token = token)
                .customEnqueue(
                    onSuccess = {
                        profileName.value = it.nickName
                        lastProfileName = it.nickName
                        introduce.value = it.info
                        imgIndex.value = (it.imgIndex) -1
                        Log.e("ProfileActivity","index : ${it.imgIndex}")
                        Log.e("ProfileActivity","img : ${it.profileImg}")
                        isCalledProfile.value = true
                    },
                    onError = {
                        Log.e("ProfileActivity","fun callMyInfo ERROR : ${it.code()}")
                    }
                )
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}