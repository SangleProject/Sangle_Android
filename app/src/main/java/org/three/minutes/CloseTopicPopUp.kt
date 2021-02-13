package org.three.minutes

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import org.three.minutes.databinding.CloseWrittenPopUpBinding
import org.three.minutes.home.ui.HomeActivity
import org.three.minutes.writing.ui.WritingReadyActivity

class CloseTopicPopUp(context : Context, private val topic : String) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = CloseWrittenPopUpBinding.inflate(layoutInflater)
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

        binding.popupCancel.setOnClickListener {
            dismiss()
        }

        binding.popupButton.setOnClickListener {
            val intent = Intent(context,WritingReadyActivity::class.java)
            intent.putExtra("topic",topic)
            context.startActivity(intent)
        }
    }
}