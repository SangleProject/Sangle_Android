package org.three.minutes.badge.viewmodel

import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.ViewModel
import org.three.minutes.badge.data.BadgeListData

class BadgeViewModel : ViewModel() {

    //뱃지 리스트를 담아놓은 리스트
    var badgeList = mutableListOf<BadgeListData>()

}