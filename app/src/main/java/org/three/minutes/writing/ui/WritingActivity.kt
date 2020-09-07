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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.three.minutes.R
import org.three.minutes.databinding.ActivityWritingBinding
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
                if (count == 30) {
                    Toast.makeText(this, "under 30", Toast.LENGTH_SHORT).show()
                }
                else if (count == 0){
                    Log.d("checkTimer","good 0")
                }
            })

        countingWrite()

//      글자 수 카운팅
        mBinging.writingContentsEdt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, count: Int) {
                if ( s.isNullOrBlank()){
                    mViewModel.writingCount.postValue(0)
                }
                else{
                    mViewModel.writingCount.postValue(s.length)
                }
            }
        })

        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun countingWrite() {
        val count = mBinging.writingContentsEdt.text.toString()
        if ( count.isBlank()){
            mViewModel.writingCount.value = 0
        }
        else{
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