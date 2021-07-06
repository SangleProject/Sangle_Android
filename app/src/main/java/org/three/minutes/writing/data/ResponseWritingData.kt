package org.three.minutes.writing.data

data class ResponseWritingData(
    val badge: List<BadgeData>,
    val postIdx: Int,
    val postWrite: String,
    val topic: String
)