package org.three.minutes.singleton

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatDialog
import org.three.minutes.R

object PopUpObject {

    // 로딩 팝업
    fun setLoading(activity: Activity) : AppCompatDialog{
        val progress = AppCompatDialog(activity)
        progress.apply {
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            setContentView(R.layout.loading_dialog)
        }
        return progress
    }

    // 제출 팝업
    fun showComplete(activity: Activity) : AppCompatDialog{
        val progress = AppCompatDialog(activity)
        progress.apply {
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            setCanceledOnTouchOutside(true)
            setContentView(R.layout.writing_complete_popup)
        }

        return progress
    }

    fun showTimeOver(activity: Activity) : AppCompatDialog{
        val progress = AppCompatDialog(activity)
        progress.apply {
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
            setContentView(R.layout.writing_timeover_popup)
            return progress
        }
    }
}