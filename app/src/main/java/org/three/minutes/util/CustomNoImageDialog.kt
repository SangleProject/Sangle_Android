package org.three.minutes.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.WindowManager
import com.bumptech.glide.Glide
import org.three.minutes.databinding.DialogCustomBinding
import org.three.minutes.databinding.DialogCustomNoImgBinding

class CustomNoImageDialog(
    context: Context,
    private val title: String,
    private val content: String,
    private val cancelTitle: String,
    private val okTitle: String,
    private val isCancelable: Boolean = true
) : Dialog(context) {

    private var clickListener: ClickListener? = null
    private val binding by lazy {
        DialogCustomNoImgBinding.inflate(layoutInflater)
    }

    interface ClickListener {
        fun setOnOk(dialog: Dialog)
        fun setOnCancel(dialog: Dialog)
    }

    fun setDialogClickListener(l: ClickListener) {
        clickListener = l
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setCanceledOnTouchOutside(isCancelable)
        setCancelable(isCancelable)

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
        binding.run {
            txtTitle.text = title
            txtContents.text = content
            btnCancel.text = cancelTitle
            btnOk.text = okTitle
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
    }
}