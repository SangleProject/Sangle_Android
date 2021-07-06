package org.three.minutes.writing.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class BadgeData(
    val badgeName: String,
    val badgeInfo: String,
    val badgeImg: String
) : Serializable