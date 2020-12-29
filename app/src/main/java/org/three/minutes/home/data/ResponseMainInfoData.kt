package org.three.minutes.home.data

data class ResponseMainInfoData(
    val badge: List<Badge>,
    val compare: String,
    val nickName: String,
    val percentage: Int,
    val postCount: Int,
    val profileImg: String,
    val remaining: Int,
    val week: Int
) {
    data class Badge(
        val badgeImg: String,
        val badgeInfo: String,
        val badgeName: String
    )
}