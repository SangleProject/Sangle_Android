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
                        introduce.value = it.info
                        imgIndex.value = (it.imgIndex) -1
//                        Log.e("ProfileActivity","index : ${it.imgIndex}")
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