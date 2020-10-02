package org.three.minutes.writing.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import kotlinx.android.synthetic.main.writing_complete_popup.*
import kotlinx.android.synthetic.main.writing_timeover_popup.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityWritingBinding
import org.three.minutes.singleton.PopUpObject
import org.three.minutes.util.margin
import org.three.minutes.util.textCheckListener
import org.three.minutes.writing.viewmodel.WritingViewModel

class WritingActivity : AppCompatActivity() {
    private lateinit var mBinging: ActivityWritingBinding
    private lateinit var mImm: InputMethodManager
    private val mViewModel: WritingViewModel by viewModels()
    private lateinit var mCompletePopup: AppCompatDialog
    private lateinit var mTimeoverPopup: AppCompatDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinging = DataBindingUtil.setContentView(this, R.layout.activity_writing)
        mBinging.lifecycleOwner = this
        mBinging.activity = this
        mViewModel.timerThreeMin()
        mBinging.viewmodel = mViewModel
        mCompletePopup = PopUpObject.showComplete(this)
        mTimeoverPopup = PopUpObject.showTimeOver(this)


        //팝업 클릭 리스너 세팅
        initPopup()

        mViewModel.timerCount.observe(this,
            { count ->
                if (count == 3) {
                    mBinging.timerLayout.setBackgroundResource(R.drawable.timer_red)
                    mBinging.apply {
                        t2.setTextColor(ContextCompat.getColor(mBinging.root.context, R.color.red))
                        t3.setTextColor(ContextCompat.getColor(mBinging.root.context, R.color.red))
                        writingTimerTxt.setTextColor(
                            ContextCompat.getColor(
                                mBinging.root.context,
                                R.color.red
                            )
                        )
                        t4.setTextColor(ContextCompat.getColor(mBinging.root.context, R.color.red))
                    }
                } else if (count == 0) {
                    if(mCompletePopup.isShowing){
                        mCompletePopup.dismiss()
                    }
                    mTimeoverPopup.show()
                }
            })

        // 글씨 카운팅 관련 함수
        countingWrite()

        // 키보드가 올라오는 것에 따라 타이머 위치 변경
        TedKeyboardObserver(this).listen {
            if (it) {
                mBinging.timerLayout.margin(bottom = 4F)

                Log.d("checkKeyboard", "open")
            } else {
                mBinging.timerLayout.margin(bottom = 60F)
            }
        }

        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

    }

    private fun initPopup() {
        mCompletePopup.apply {
            complete_stop_btn.setOnClickListener {
                Toast.makeText(this@WritingActivity, "ok", Toast.LENGTH_SHORT).show()
            }
            complete_close_btn.setOnClickListener {
                dismiss()
            }
            complete_continue_btn.setOnClickListener {
                dismiss()
            }
        }

        mTimeoverPopup.apply {
            timeover_stop_btn.setOnClickListener {
                Toast.makeText(this@WritingActivity, "go to complete"
                    , Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun countingWrite() {
        val count = mBinging.writingContentsEdt.text.toString()
        if (count.isBlank()) {
            mViewModel.writingCount.value = 0
        } else {
            mViewModel.writingCount.value = count.length
        }

        //      글자 수 카운팅
        mBinging.writingContentsEdt.textCheckListener { s ->
            if (s.isNullOrBlank()) {
                mViewModel.writingCount.postValue(0)
            } else {
                mViewModel.writingCount.postValue(s.length)
            }

        }
    }

    // 작성 공간 외 다른 공간 클릭 시 키보드 내리고 포커스 해제
    fun clearAllFocus(view: View) {
        if (mImm.isAcceptingText) {
            view.clearFocus()
            mImm.hideSoftInputFromWindow(mBinging.writingContentsEdt.windowToken, 0)

        }
    }

    fun showCompletePopup() {
        mCompletePopup.show()

    }
}