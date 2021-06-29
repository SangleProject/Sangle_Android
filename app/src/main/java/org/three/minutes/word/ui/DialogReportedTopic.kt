package org.three.minutes.word.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import min.dev.singleclick.mingSingleClickListener
import org.three.minutes.databinding.DialogReportedTopicBinding
import org.three.minutes.detail.data.ResponseMyWritingData
import org.three.minutes.detail.data.ResponseOtherWritingData

class DialogReportedTopic(context: Context) : Dialog(context) {

    private val binding by lazy {
        DialogReportedTopicBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    fun showMyReportedTopic(data: ResponseMyWritingData) {
        binding.txtReportTitle.text = "\"${data.topic}\" 으로 \n 작성한 게시글이 숨겨졌어요."
        binding.txtContents.text = data.isBan
        show()
    }

    @SuppressLint("SetTextI18n")
    fun showOtherReportedTopic(data: ResponseOtherWritingData) {
        binding.txtReportTitle.text = "\"${data.topic}\" 으로 \n 작성한 게시글이 숨겨졌어요."
        binding.txtContents.text = data.isBan
        show()
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
            dismiss()
        }
    }
}