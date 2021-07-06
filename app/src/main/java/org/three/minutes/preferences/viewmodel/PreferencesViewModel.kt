package org.three.minutes.preferences.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.three.minutes.ThreeApplication
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.SangleDataStoreManager
import org.three.minutes.util.customEnqueue


class PreferencesViewModel : ViewModel() {

    val _token = ThreeApplication.getInstance().getDataStore().token.asLiveData()
    var token = ""

    var isDeleteMemberShip = MutableLiveData(false)

    val getNotificationPush = ThreeApplication.getInstance().getDataStore().isNotification.asLiveData()
    val getMotivePush = ThreeApplication.getInstance().getDataStore().isMotive.asLiveData()


    var isNotification = MutableLiveData<Boolean>()
    var isMotive = MutableLiveData<Boolean>()

    fun callDeleteMembership(){
        viewModelScope.launch {
            SangleServiceImpl.service.deleteMembership(token)
                .customEnqueue(
                    onSuccess = {
                        isDeleteMemberShip.postValue(true)
                    },
                    onError = {
                        Log.e("DeleteMembership","ERROR : ${it.code()}")
                    }
                )
        }
    }
}