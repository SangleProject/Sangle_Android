package org.three.minutes.writing.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityWritingBinding

class WritingActivity : AppCompatActivity() {
    private lateinit var mBinging: ActivityWritingBinding
    private lateinit var mImm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinging = DataBindingUtil.setContentView(this, R.layout.activity_writing)
        mBinging.lifecycleOwner = this
        mBinging.activity = this
        
        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

// 작성 공간 외 다른 공간 클릭 시 키보드 내리고 포커스 해제
    fun clearAllFocus(view: View) {
        if (mImm.isAcceptingText) {
            view.clearFocus()
            mImm.hideSoftInputFromWindow(mBinging.writingContentsEdt.windowToken, 0)
        }
    }
}