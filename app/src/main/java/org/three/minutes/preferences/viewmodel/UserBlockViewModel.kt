package org.three.minutes.preferences.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _isSuccessDeleteBlockUser = MutableLiveData<Boolean>()
    val isSuccessDeleteBlockUser: LiveData<Boolean> = _isSuccessDeleteBlockUser

    private var _deletedBlockUser: ResponseBlockedUser? = null
    val deletedBlockUser: ResponseBlockedUser?
    get() = _deletedBlockUser

    private val _deleteBlockUserError = MutableLiveData<Int>()
    val deleteBlockUserErrorCode: LiveData<Int> = _deleteBlockUserError


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

    fun deleteBlockUser(data: ResponseBlockedUser, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userUseCase.deleteBlockUser(data.blockedIdx).catch {
                when(it) {
                    is HttpException -> {
                        _deleteBlockUserError.postValue(it.code())
                    }
                    else -> {
                        it.printStackTrace()
                        _deleteBlockUserError.postValue(501)
                    }
                }
            }.collect {
                if (it) {
                    _deletedBlockUser = data

                    val userList = blockedUsers.value?.toMutableList() ?: mutableListOf()
                    Log.i("현재 차단 목록", userList.toString())
                    if (userList.isNotEmpty()) {
                        Log.i("차단해제할 포지션", position.toString())
                        userList.removeAt(position)
                        Log.i("차단해제 지우고 나서 차단 목록", userList.toString())
                        blockedUsers.postValue(userList)
                        Log.i("최종 차단 목록", blockedUsers.value.toString())
                    }

                    _isSuccessDeleteBlockUser.postValue(it)
                }
            }
        }

    }

}