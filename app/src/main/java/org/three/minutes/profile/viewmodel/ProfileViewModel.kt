package org.three.minutes.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.three.minutes.R
import org.three.minutes.profile.data.ProfileData

class ProfileViewModel : ViewModel() {
    var introduce = MutableLiveData("")
    var introduceCount = MutableLiveData(0)

    val profileImgList = mutableListOf<ProfileData>(
        ProfileData(0, R.drawable.profile1),
        ProfileData(0, R.drawable.profile2),
        ProfileData(0, R.drawable.profile3),
        ProfileData(0, R.drawable.profile4),
        ProfileData(0, R.drawable.profile5),
        ProfileData(0, R.drawable.profile6),
        ProfileData(0, R.drawable.profile7),
        ProfileData(0, R.drawable.profile8)
    )
}