package org.three.minutes.writing.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_writing_result.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityWritingResultBinding
import org.three.minutes.writing.viewmodel.WritingResultViewModel

class WritingResultActivity : AppCompatActivity() {

    private val mBinding : ActivityWritingResultBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.activity_writing_result)
    }
    private val mViewModel : WritingResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_writing_result)
        mBinding.apply {
            lifecycleOwner = this@WritingResultActivity
            viewModel = mViewModel
        }

        setSupportActionBar(result_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val intent = intent
        mViewModel.contents.value = intent.getStringExtra("contents")
        mViewModel.topic.value = intent.getStringExtra("topic")

        // 글 내용 TextView 스크롤
        mBinding.resultContentsTxt.movementMethod = ScrollingMovementMethod()
    }
}