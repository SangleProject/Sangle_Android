package org.three.minutes.preferences.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import min.dev.singleclick.mingSingleClickListener
import org.three.minutes.*
import org.three.minutes.databinding.ActivityPreferencesBinding
import org.three.minutes.login.ui.MainActivity
import org.three.minutes.preferences.viewmodel.PreferencesViewModel
import org.three.minutes.singleton.GoogleLoginObject
import org.three.minutes.singleton.StatusObject
import org.three.minutes.signup.ui.TermsActivity
import org.three.minutes.util.AppVersionChecker
import org.three.minutes.util.showToast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
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
        MembershipWithdrawalPopUp(this, this)
    }

    private val logOutPopUp: LogOutPopUp by lazy {
        LogOutPopUp(this)
    }

    // 회원탈퇴 팝업 오케이 눌렀을 시 로직
    override fun callWithdrawal(dialog: Dialog) {
        mViewModel.callDeleteMembership()
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        StatusObject.setStatusBar(this)

        compareAppVersion()

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
            logOutPopUp.setClickListener(object : LogOutPopUp.PopUpClickListener {
                override fun setOnCancel() {
                }

                override fun setOnOk() {
                    startGoogleLogout()
                }
            })

            logOutPopUp.show()
        }

        mBinding.configurationWithdrawalTxt.setOnClickListener {
            withDrawalPopUp.show()
        }

        mBinding.personalInfo.setOnClickListener {
            val intent = Intent(this, TermsActivity::class.java)
            intent.putExtra("title", "개인정보보호 정책")
            startActivity(intent)
        }

        mBinding.serviceTerm.setOnClickListener {
            val intent = Intent(this, TermsActivity::class.java)
            intent.putExtra("title", "서비스 이용약관")
            startActivity(intent)
        }

        mBinding.layoutVersion.mingSingleClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://play.google.com/store/apps/details?id=${packageName}")
            }
            startActivity(intent)
        }
        mBinding.serviceBlockedUser.mingSingleClickListener {
            startActivity(Intent(this, UserBlockActivity::class.java))
        }

        setObserve()
        setToolbar()
    }

    private fun setObserve() {
        mViewModel._token.observe(this, {
            mViewModel.token = it
        })

        mViewModel.isDeleteMemberShip.observe(this, {
            if (it) {
                startGoogleLogout()
                if (withDrawalPopUp.isShowing) {
                    withDrawalPopUp.dismiss()
                }
            }
        })
    }

    private fun startGoogleLogout() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("LogOut", GoogleLoginObject.GoogleLogInCode.LOG_OUT_CODE.code)
        startActivity(intent)
        finishAndRemoveTask()
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

    @SuppressLint("SetTextI18n")
    private fun compareAppVersion() {
        lifecycleScope.launch(Dispatchers.IO) {
            var deviceVersion = ""
            var storeVersion = ""
            storeVersion = AppVersionChecker.getMarketVersion(packageName) ?: ""
            deviceVersion = packageManager.getPackageInfo(packageName, 0).versionName

            val compareJob =
                launch(context = Dispatchers.Main, start = CoroutineStart.LAZY) compare@{
                    if (deviceVersion.isBlank() || storeVersion.isBlank()) {
                        mBinding.configurationVersionTxt.text = "버전 정보"
                        mBinding.configurationVersionRecentTxt.text =
                            getString(R.string.version_recent)
                        return@compare
                    }

                    mBinding.configurationVersionTxt.text = "버전 정보 V $deviceVersion"
                    if (storeVersion > deviceVersion) {
                        mBinding.configurationVersionRecentTxt.text =
                            getString(R.string.have_recent_version)
                    }
                    else {
                        mBinding.configurationVersionRecentTxt.text =
                            getString(R.string.version_recent)
                    }
                }




            try {
                Log.i("기기버전", deviceVersion)
                Log.i("마켓버전", storeVersion)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            compareJob.start()
        }
    }
}
