package org.three.minutes.writing.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import gun0912.tedkeyboardobserver.TedRxKeyboardObserver
import org.three.minutes.R
import org.three.minutes.databinding.ActivityWritingBinding
import org.three.minutes.util.margin
import org.three.minutes.util.textCheckListener
import org.three.minutes.writing.viewmodel.WritingViewModel

class WritingActivity : AppCompatActivity() {
    private lateinit var mBinging: ActivityWritingBinding
    private lateinit var mImm: InputMethodManager
    private val mViewModel: WritingViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinging = DataBindingUtil.setContentView(this, R.layout.activity_writing)
        mBinging.lifecycleOwner = this
        mBinging.activity = this
        mViewModel.timerThreeMin()
        mBinging.viewmodel = mViewModel


        mViewModel.timerCount.observe(this,
            Observer<Int> { count ->
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
                }
            })

        countingWrite()

        TedKeyboardObserver(this).listen {
            if (it) {

                mBinging.timerLayout.margin(bottom = 4F)

                Log.d("checkKeyboard", "open")
            } else {
                mBinging.timerLayout.margin(bottom = 60F)
            }
        }

//      글자 수 카운팅
        mBinging.writingContentsEdt.textCheckListener { s ->
            if (s.isNullOrBlank()) {
                mViewModel.writingCount.postValue(0)
            } else {
                mViewModel.writingCount.postValue(s.length)
            }

        }

        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

    }


    private fun countingWrite() {
        val count = mBinging.writingContentsEdt.text.toString()
        if (count.isBlank()) {
            mViewModel.writingCount.value = 0
        } else {
            mViewModel.writingCount.value = count.length
        }
    }

    // 작성 공간 외 다른 공간 클릭 시 키보드 내리고 포커스 해제
    fun clearAllFocus(view: View) {
        if (mImm.isAcceptingText) {
            view.clearFocus()
            mImm.hideSoftInputFromWindow(mBinging.writingContentsEdt.windowToken, 0)

        }
    }
}