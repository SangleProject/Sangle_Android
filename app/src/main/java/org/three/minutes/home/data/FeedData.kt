package org.three.minutes.home.data

import java.io.Serializable

data class FeedData (
    val title : String,
    val date : String,
    val contents : String,
    val profileId : String,
    val count : Int,
    val isMy : Boolean
) : Serializable