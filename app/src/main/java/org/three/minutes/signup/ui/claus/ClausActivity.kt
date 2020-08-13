package org.three.minutes.signup.ui.claus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_claus.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityClausBinding
import org.three.minutes.signup.viewmodel.ClausViewModel

class ClausActivity : AppCompatActivity() {
    private val mViewModel : ClausViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityClausBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_claus)
        val title = intent.getStringExtra("title")
        mViewModel.layoutTitle.value = title
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        val transaction = supportFragmentManager.beginTransaction()
        when(mViewModel.layoutTitle.value){
            "개인정보보호 정책" -> {
                transaction.add(R.id.claus_contents_layout, PrivacyFragment()).commit()
            }
            "서비스 이용약관" -> {
                transaction.add(R.id.claus_contents_layout, ServiceFragment()).commit()
            }
        }

        claus_back_img.setOnClickListener {
            finish()
        }
    }
}