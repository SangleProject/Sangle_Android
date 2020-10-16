package org.three.minutes.writing.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
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
    private lateinit var mBinding: ActivityWritingBinding
    private lateinit var mImm: InputMethodManager
    private val mViewModel: WritingViewModel by viewModels()
    private lateinit var mCompletePopup: AppCompatDialog
    private lateinit var mTimeoverPopup: AppCompatDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_writing)
        mBinding.lifecycleOwner = this
        mBinding.activity = this
        mViewModel.timerThreeMin()
        mBinding.viewmodel = mViewModel
        mCompletePopup = PopUpObject.showComplete(this)
        mTimeoverPopup = PopUpObject.showTimeOver(this)


        //팝업 클릭 리스너 세팅
        initPopup()

        
        // 타이머의 시간에 따라 UI 변경
        mViewModel.timerCount.observe(this,
            { count ->
                if (count == 3) {
                    mBinding.timerLayout.setBackgroundResource(R.drawable.timer_red)
                    mBinding.apply {
                        t2.setTextColor(ContextCompat.getColor(mBinding.root.context, R.color.red))
                        t3.setTextColor(ContextCompat.getColor(mBinding.root.context, R.color.red))
                        writingTimerTxt.setTextColor(
                            ContextCompat.getColor(
                                mBinding.root.context,
                                R.color.red
                            )
                        )
                        t4.setTextColor(ContextCompat.getColor(mBinding.root.context, R.color.red))
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
                mBinding.timerLayout.margin(bottom = 4F)

                Log.d("checkKeyboard", "open")
            } else {
                mBinding.timerLayout.margin(bottom = 60F)
            }
        }

        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

    }

    private fun initPopup() {
        mCompletePopup.apply {
            complete_stop_btn.setOnClickListener {
                val intent = Intent(this@WritingActivity, WritingResultActivity::class.java)
                intent.putExtra("contents",mBinding.writingContentsEdt.text.toString())
                startActivity(intent)
                finish()
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
                val intent = Intent(this@WritingActivity, WritingResultActivity::class.java)
                intent.putExtra("contents",mBinding.writingContentsEdt.text.toString())
                startActivity(intent)
                finish()
            }
            timeover_close_btn.setOnClickListener {
                val intent = Intent(this@WritingActivity, WritingResultActivity::class.java)
                intent.putExtra("contents",mBinding.writingContentsEdt.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }


    private fun countingWrite() {
        val count = mBinding.writingContentsEdt.text.toString()
        if (count.isBlank()) {
            mViewModel.writingCount.value = 0
        } else {
            mViewModel.writingCount.value = count.length
        }

        //      글자 수 카운팅
        mBinding.writingContentsEdt.textCheckListener { s ->
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
            mImm.hideSoftInputFromWindow(mBinding.writingContentsEdt.windowToken, 0)

        }
    }

    fun showCompletePopup() {
        mCompletePopup.show()

    }

    // 취소 버튼 누르면 뒤로 가기
    fun clickCancleBtn(){
        finish()
    }
}