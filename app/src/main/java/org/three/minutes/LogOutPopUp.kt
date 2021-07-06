package org.three.minutes

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import org.three.minutes.databinding.PopupLogoutBinding

class LogOutPopUp(context: Context): Dialog(context) {

    private var clickListener: PopUpClickListener? = null

    interface PopUpClickListener{
        fun setOnCancel()
        fun setOnOk()
    }

    fun setClickListener(listener: PopUpClickListener) {
        clickListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PopupLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)

        // dialog의 표시 영역이 기본적으로 화면 전체가 아니므로 원하는대로 안보임
        // 그래서 직접 dialog 표시 영역을 설정
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        window?.attributes = lp

        binding.logoutCancelBtn.setOnClickListener {
            clickListener?.setOnCancel()
            dismiss()
        }

        binding.logoutOkBtn.setOnClickListener {
            clickListener?.setOnOk()
            dismiss()
        }

    }
}