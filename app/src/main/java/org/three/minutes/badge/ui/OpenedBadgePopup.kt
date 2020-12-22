package org.three.minutes.badge.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import kotlinx.android.synthetic.main.badge_opened_popup.*
import org.three.minutes.R

class OpenedBadgePopup(context : Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.badge_opened_popup)


        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        badge_cancle.setOnClickListener {
            dismiss()
        }
    }
}
