package org.three.minutes.home.data

import org.three.minutes.writing.data.BadgeData

data class ResponseMainInfoData(
    val badge: List<BadgeData>,
    val compare: String,
    val nickName: String,
    val percentage: Int,
    val postCount: Int,
    val profileImg: String,
    val remaining: Int,
    val week: Int
)