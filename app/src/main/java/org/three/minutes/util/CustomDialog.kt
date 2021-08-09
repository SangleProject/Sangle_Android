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

class CustomDialog(
    context: Context,
    private val title: String,
    private val content: String,
    private val cancelTitle: String,
    private val okTitle: String,
    private val dialogImg: Drawable
) : Dialog(context) {

    private var clickListener: ClickListener? = null
    private val binding by lazy {
        DialogCustomBinding.inflate(layoutInflater)
    }

    interface ClickListener {
        fun setOnOk(dialog: Dialog)
    }

    fun setDialogClickListener(l: ClickListener) {
        clickListener = l
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
        Glide.with(binding.root).load(dialogImg).into(binding.completeImg)
    }

    private fun clickEvent() {
        binding.btnOk.setOnClickListener {
            clickListener?.setOnOk(this)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}