package org.three.minutes.preferences.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityUserBlockBinding
import org.three.minutes.preferences.viewmodel.UserBlockViewModel
import org.three.minutes.singleton.StatusObject

class UserBlockActivity : AppCompatActivity() {
    private val binding: ActivityUserBlockBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_user_block)
    }
    private val userBlockViewModel: UserBlockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            lifecycleOwner = this@UserBlockActivity
        }

        StatusObject.setStatusBar(this)
        setObserve()
        clickEvent()
    }

    private fun setObserve() {

    }

    private fun clickEvent() {

    }
}