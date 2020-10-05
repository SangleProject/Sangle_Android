package org.three.minutes.writing.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_writing_ready.*
import kotlinx.coroutines.*
import org.three.minutes.R
import kotlin.coroutines.CoroutineContext

class WritingReadyActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing_ready)
        job = Job()

        ready_lottie.apply {
            setAnimation("keyword_aos.json")

        }
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
}