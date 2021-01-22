package org.three.minutes.badge.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.badge_closed_popup.*
import org.three.minutes.R
import org.three.minutes.writing.data.BadgeData

class ClosedBadgePopup(context : Context, private val data : BadgeData) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.badge_closed_popup)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)

        // dialog의 표시 영역이 기본적으로 화면 전체가 아니므로 원하는대로 안보임
        // 그래서 직접 dialog 표시 영역을 설정
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        window?.attributes = lp

        Glide.with(context).load(data.badgeImg).into(close_badge_img)
        close_badge_title.text = data.badgeName
        close_badge_challenge.text = data.badgeInfo

        close_badge_cancel.setOnClickListener {
            dismiss()
        }
    }
}