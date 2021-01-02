package org.three.minutes.writing.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_writing_result.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityWritingResultBinding
import org.three.minutes.home.ui.HomeActivity
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

        mViewModel.getCurrentTime()
        mViewModel.contents.value = intent.getStringExtra("contents")
        mViewModel.topic.value = intent.getStringExtra("topic")
        mViewModel.contentsCount.value = mViewModel.contents.value?.length ?: 0


        // 글 내용 TextView 스크롤
        mBinding.resultContentsTxt.movementMethod = ScrollingMovementMethod()

        // 좌측 상단 완료 버튼
        mBinding.resultDone.setOnClickListener {
            startHomeActivity()
        }
        // 삭제하기 버튼
        mBinding.resultDeleteTxt.setOnClickListener {
            startHomeActivity()
        }

    }

    // 완료 버튼, 삭제 버튼, 뒤로가기 버튼 클릭 시 호출
    private fun startHomeActivity(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finishAndRemoveTask()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startHomeActivity()
    }
}