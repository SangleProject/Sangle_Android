
package org.three.minutes.preferences.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityPreferencesBinding
import org.three.minutes.singleton.StatusObject
import javax.net.ssl.SSLEngineResult

class PreferencesActivity : AppCompatActivity() {
    private val mBinding : ActivityPreferencesBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.activity_preferences)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StatusObject.setStatusBar(this)

        mBinding.apply {
            lifecycleOwner = this@PreferencesActivity
        }

        mBinding.notificationAllSwitch.isEnabled = false

    }
}