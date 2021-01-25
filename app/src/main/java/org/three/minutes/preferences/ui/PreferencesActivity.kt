package org.three.minutes.preferences.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivityPreferencesBinding
import org.three.minutes.preferences.viewmodel.PreferencesViewModel
import org.three.minutes.singleton.StatusObject
import kotlin.coroutines.CoroutineContext

class PreferencesActivity : AppCompatActivity(), CoroutineScope {
    private val mBinding: ActivityPreferencesBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_preferences)
    }

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mViewModel: PreferencesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        StatusObject.setStatusBar(this)

        mViewModel.getNotificationPush.observe(this, {
            mViewModel.isNotification.value = it
        })

        mViewModel.getMotivePush.observe(this, {
            mViewModel.isMotive.value = it
        })


        mBinding.apply {
            lifecycleOwner = this@PreferencesActivity
            viewModel = mViewModel
        }

        mBinding.notificationNoticeSwitch.setOnCheckedChangeListener { _, isChecked ->
            launch {
                ThreeApplication.getInstance().getDataStore().setNotificationPush(isChecked)
            }
        }

        mBinding.notificationMotiveSwitch.setOnCheckedChangeListener { _, isChecked ->
            launch {
                ThreeApplication.getInstance().getDataStore().setMotivePush(isChecked)
            }
        }

        setToolbar()
    }

    private fun setToolbar() {
        // 뒤로가기 키 누르면 뒤로가기
        mBinding.drawToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
