package org.three.minutes.preferences.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.three.minutes.architect.data.ResponseBlockedUser
import org.three.minutes.architect.domain.usecase.UserUseCase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class UserBlockViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _getBlockedUserListApiCode = MutableLiveData<Int>()
    val getBlockedUserApiErrorCode: LiveData<Int> = _getBlockedUserListApiCode

    private val blockedUsers = MutableLiveData<List<ResponseBlockedUser>>()
    val blockedUsersLiveData: LiveData<List<ResponseBlockedUser>> = blockedUsers


    fun getBlockedUserList() {
        viewModelScope.launch {
            userUseCase.getBlockedUserList().catch {
                when(it) {
                    is HttpException -> {
                        _getBlockedUserListApiCode.postValue(it.code())
                    }
                    is IOException -> {
                        _getBlockedUserListApiCode.postValue(500)
                    }
                }
            }.collect {
                blockedUsers.postValue(it)
            }
        }
    }

}