package org.three.minutes.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import org.three.minutes.ThreeApplication
import org.three.minutes.architect.domain.usecase.UserUseCase
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.profile.data.ResponseDiffProfileData
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.customEnqueue
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class OtherProfileViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val getToken = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""
    var userName = MutableLiveData("")

    var diffInfo = MutableLiveData<ResponseDiffProfileData>()
    var diffFeedList = MutableLiveData<List<ResponseOtherWritingData>>(listOf())

    var diffFeedRecent = MutableLiveData<List<ResponseOtherWritingData>>(listOf())
    var diffFeedPopular = MutableLiveData<List<ResponseOtherWritingData>>(listOf())

    var filter = MutableLiveData("최신순")

    private val _postBlockUser = MutableLiveData<Boolean>()
    val postBlockUserSuccess: LiveData<Boolean> = _postBlockUser

    private val _postBlockUserStatus = MutableLiveData<Int>()
    val postBlockUserErrorCode: LiveData<Int> = _postBlockUserStatus


    fun callDiffInfo() {
        viewModelScope.launch {
            SangleServiceImpl.service.getDiffProfileInfo(
                token = token,
                nickName = userName.value ?: ""
            )
                .customEnqueue(
                    onSuccess = {
                        diffInfo.value = it
                    },
                    onError = {
                        Log.e("OtherProfileActivity", "fun callDiffInfo() ERROR : ${it.code()}")
                    }
                )
        }
    }

    fun callDiffFeedRecent() {
        viewModelScope.launch {
            SangleServiceImpl.service.getDiffFeedRecent(
                token = token,
                nickName = userName.value ?: ""
            )
                .customEnqueue(
                    onSuccess = {
                        diffFeedRecent.value = it
                        setRcvRecent()
                    },
                    onError = {
                        Log.e(
                            "OtherProfileActivity",
                            "fun callDiffFeedRecent() ERROR : ${it.code()}"
                        )

                    }
                )
        }
    }

    fun callDiffFeedPopular() {
        viewModelScope.launch {
            SangleServiceImpl.service.getDiffFeedRecent(
                token = token,
                nickName = userName.value ?: ""
            )
                .customEnqueue(
                    onSuccess = {
                        diffFeedPopular.value = it
                    },
                    onError = {
                        Log.e(
                            "OtherProfileActivity",
                            "fun callDiffFeedRecent() ERROR : ${it.code()}"
                        )

                    }
                )
        }
    }

    fun setRcvRecent() {
        diffFeedList.value = diffFeedRecent.value
    }

    fun setRcvPopular() {
        diffFeedList.value = diffFeedPopular.value
    }

    @ExperimentalCoroutinesApi
    fun postUserBlock() {
        viewModelScope.launch {
            userUseCase.postBlockUser(diffInfo.value?.userIdx ?: -1).catch {
                when(it) {
                    is HttpException -> {
                        _postBlockUserStatus.postValue(it.code())
                    }
                    else -> {
                        _postBlockUserStatus.postValue(501)
                    }
                }
            }.collect {
                _postBlockUser.postValue(it)
            }
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}