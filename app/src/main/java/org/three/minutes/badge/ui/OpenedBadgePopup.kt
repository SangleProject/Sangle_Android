package org.three.minutes.badge.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.badge_opened_popup.*
import org.three.minutes.R
import org.three.minutes.writing.data.BadgeData

class OpenedBadgePopup(context : Context, private val badgeData : BadgeData) : Dialog(context) {

    interface SetOnClickListener{
        fun onCancelClick(dialog : Dialog)
    }
    private lateinit var listener : SetOnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.badge_opened_popup)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)

        // dialog의 표시 영역이 기본적으로 화면 전체가 아니므로 원하는대로 안보임
        // 그래서 직접 dialog 표시 영역을 설정
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        window?.attributes = lp

        setPopUp()

        badge_cancle.setOnClickListener {
            listener.onCancelClick(this)
        }
    }

    private fun setPopUp() {
        Glide.with(context).load(badgeData.badgeImg).into(badge_img)
        badge_title.text = badgeData.badgeName
        badge_info.text = badgeData.badgeInfo
    }

    fun setCancelClick(listener : SetOnClickListener){
        this.listener = listener
    }
}
