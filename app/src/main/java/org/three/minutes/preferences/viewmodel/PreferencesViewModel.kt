package org.three.minutes.preferences.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.three.minutes.ThreeApplication


class PreferencesViewModel : ViewModel() {

    val getNotificationPush = ThreeApplication.getInstance().getDataStore().isNotification.asLiveData()
    val getMotivePush = ThreeApplication.getInstance().getDataStore().isMotive.asLiveData()


    var isNotification = MutableLiveData<Boolean>()
    var isMotive = MutableLiveData<Boolean>()
}