package org.three.minutes.writing.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityWritingEditBinding
import org.three.minutes.writing.viewmodel.WritingEditViewModel

class WritingEditActivity : AppCompatActivity() {
    private val mViewModel : WritingEditViewModel by viewModels()
    private val mBinding : ActivityWritingEditBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_writing_edit)
    }
    private lateinit var mImm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@WritingEditActivity
            viewModel = mViewModel
        }
        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // 글자수 카운팅
        checkContentsCount()
    }

    private fun checkContentsCount() {
        mViewModel.contents.observe(this,{
            mViewModel.contentsCount.value = it.length
        })
    }

    // 작성 공간 외 다른 공간 클릭 시 키보드 내리고 포커스 해제
    fun clearAllFocus(view: View) {
        if (mImm.isAcceptingText) {
            view.clearFocus()
            mImm.hideSoftInputFromWindow(mBinding.writingContentsEdt.windowToken, 0)

        }
    }
}