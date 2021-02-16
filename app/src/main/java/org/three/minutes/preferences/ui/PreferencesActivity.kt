package org.three.minutes.preferences.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.MembershipWithdrawalPopUp
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivityPreferencesBinding
import org.three.minutes.login.ui.MainActivity
import org.three.minutes.preferences.viewmodel.PreferencesViewModel
import org.three.minutes.singleton.GoogleLoginObject
import org.three.minutes.singleton.StatusObject
import org.three.minutes.MembershipWithdrawalListener
import org.three.minutes.util.showToast
import kotlin.coroutines.CoroutineContext

class PreferencesActivity : AppCompatActivity(), CoroutineScope, MembershipWithdrawalListener {
    private val mBinding: ActivityPreferencesBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_preferences)
    }

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mViewModel: PreferencesViewModel by viewModels()
    private val withDrawalPopUp by lazy {
        MembershipWithdrawalPopUp(this,this)
    }

    // 회원탈퇴 팝업 오케이 눌렀을 시 로직
    override fun callWithdrawal(dialog: Dialog) {
        dialog.dismiss()
        showToast("회원탈퇴 완료!")
    }

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

        mBinding.configurationLogoutTxt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("LogOut", GoogleLoginObject.GoogleLogInCode.LOG_OUT_CODE.code)
            startActivity(intent)
            finishAndRemoveTask()
        }

        mBinding.configurationWithdrawalTxt.setOnClickListener {
            withDrawalPopUp.show()
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
