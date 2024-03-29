package org.three.minutes.singleton

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import org.three.minutes.R

object StatusObject {
  @SuppressLint("InlinedApi")
  @Suppress("DEPRECATION")
  fun setStatusBar(activity: Activity){
    activity.window?.decorView?.systemUiVisibility =
      View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    activity.window?.statusBarColor = Color.TRANSPARENT
  }
}