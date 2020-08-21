package org.three.minutes.singleton

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDialog
import org.three.minutes.R

object LoadingObject {
    fun setLoading(activity: Activity) : AppCompatDialog{
        val progress = AppCompatDialog(activity)
        progress.apply {
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            setContentView(R.layout.loading_dialog)
        }
        return progress
    }
}