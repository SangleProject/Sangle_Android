package org.three.minutes.writing.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityWritingReadyBinding
import kotlin.coroutines.CoroutineContext

class WritingReadyActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private val mBinding : ActivityWritingReadyBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.activity_writing_ready)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@WritingReadyActivity
            activity = this@WritingReadyActivity
        }
        job = Job()
        
        launch {
            delay(3000)

            val intent = Intent(this@WritingReadyActivity, WritingActivity::class.java)
            startActivity(intent)

            finish()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun finishActivity(){
        finish()
    }
}