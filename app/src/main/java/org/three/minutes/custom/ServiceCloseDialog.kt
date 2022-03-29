package org.three.minutes.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.WindowManager
import org.three.minutes.R
import org.three.minutes.databinding.DialogServiceCloseBinding

class ServiceCloseDialog(
    context: Context
) : Dialog(context) {

    private var clickListener: ClickListener? = null
    private val binding by lazy {
        DialogServiceCloseBinding.inflate(layoutInflater)
    }

    interface ClickListener {
        fun setOnOk(dialog: Dialog)
        fun setOnCancel(dialog: Dialog)
        fun setOnMoreClick()
    }

    fun setDialogClickListener(l: ClickListener) {
        clickListener = l
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setCanceledOnTouchOutside(false)
        setCancelable(false)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // dialog의 표시 영역이 기본적으로 화면 전체가 아니므로 원하는대로 안보임
        // 그래서 직접 dialog 표시 영역을 설정
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        window?.attributes = lp

        displayView()
        clickEvent()

    }

    private fun displayView() {
        val spannable = SpannableString(binding.txtContents2.text)

        spannable.run {
            setSpan(
                TextAppearanceSpan(context, R.style.P5_12pt_Bold),
                0,
                10,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun clickEvent() {
        binding.btnOk.setOnClickListener {
            clickListener?.setOnOk(this)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            clickListener?.setOnCancel(this)
            dismiss()
        }

        binding.btnMore.setOnClickListener {
            clickListener?.setOnMoreClick()
        }
    }
}