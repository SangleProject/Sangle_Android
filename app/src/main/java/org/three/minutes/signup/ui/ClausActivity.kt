package org.three.minutes.signup.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_claus.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityClausBinding
import org.three.minutes.signup.viewmodel.ClausViewModel
import org.three.minutes.singleton.StatusObject

class ClausActivity : AppCompatActivity() {
    private val mViewModel : ClausViewModel by viewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityClausBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_claus)
//        val title = intent.getStringExtra("title")
        val title = "서비스 이용약관"
        mViewModel.layoutTitle.value = title
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        //상태바 투명으로 만들기
        StatusObject.setStatusBar(this)

        when(mViewModel.layoutTitle.value){
            "개인정보보호 정책" -> {
                claus_web.fromAsset("SanglePrivacy.pdf")
                    .load()
            }
            "서비스 이용약관" -> {
                claus_web.fromAsset("SangleService.pdf")
                    .load()
            }
        }

        claus_back_img.setOnClickListener {
            finish()
        }
    }
}