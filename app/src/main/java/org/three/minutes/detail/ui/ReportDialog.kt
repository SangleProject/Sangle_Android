package org.three.minutes.detail.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import min.dev.singleclick.mingSingleClickListener
import org.three.minutes.databinding.DialogReportBinding

class ReportDialog(context: Context) : Dialog(context) {

    private var clickListener: PopUpClickListener? = null
    private val binding by lazy {
        DialogReportBinding.inflate(layoutInflater)
    }

    interface PopUpClickListener{
        fun setOnOk(dialog: Dialog)
    }

    fun setClickListener(listener: PopUpClickListener) {
        clickListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // dialog의 표시 영역이 기본적으로 화면 전체가 아니므로 원하는대로 안보임
        // 그래서 직접 dialog 표시 영역을 설정
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        window?.attributes = lp

        clickEvent()
    }

    private fun clickEvent() {
        binding.popupButtonOk.mingSingleClickListener {
            clickListener?.setOnOk(this)
        }

        binding.popupButtonCancel.mingSingleClickListener {
            dismiss()
        }
    }
}