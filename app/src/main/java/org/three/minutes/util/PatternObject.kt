package org.three.minutes.util

import java.util.regex.Pattern

object PatternObject {
    val ePattern = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
        Pattern.CASE_INSENSITIVE
    )

    val pPattern = Pattern.compile(
        "^(?=.*[A-Za-z])(?=.*[0-9]).{6,12}.\$",
        Pattern.CASE_INSENSITIVE
    )
}