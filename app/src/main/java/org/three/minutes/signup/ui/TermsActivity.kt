package org.three.minutes.signup.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.fragment_calender.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityTermsBinding
import org.three.minutes.signup.viewmodel.TermsViewModel
import org.three.minutes.singleton.StatusObject

class TermsActivity : AppCompatActivity() {

    private val binding : ActivityTermsBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.activity_terms)
    }
    private val mViewModel : TermsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@TermsActivity
            viewModel = mViewModel
        }
        StatusObject.setStatusBar(this)

        getTermsData()

        binding.clausToolbar.setNavigationOnClickListener {
            finish()
        }

    }

    private fun getTermsData() {
        val title = intent.getStringExtra("title").toString()
        mViewModel.layoutTitle.value = title

        when(title){
            "개인정보보호 정책" -> {
                setWebView("https://www.notion.so/acd1fc1a4f7145b59fc2fc5f1b7ac67d")
            }
            "서비스 이용약관" -> {
                setWebView("https://www.notion.so/fe3301d7074b4060b2898ef58f452770")
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView(url : String){
        binding.termsContents.webViewClient = WebViewClient()
        val webSetting = binding.termsContents.settings

        webSetting.apply {
            //새창 띄우기 허용 여부
            setSupportMultipleWindows(false)
            //자바스크립트 허용 여부
            javaScriptEnabled = true
            //자바스크립트 새창 띄우기 허용 여부
            javaScriptCanOpenWindowsAutomatically = false
            //메타태그 허용 여부
            loadWithOverviewMode = true
            //화면 사이즈 맞추기 허용 여부
            useWideViewPort = true
            //화면 줌 허용 여부
            setSupportZoom(false)
            //화면 확대 축소 허용 여부
            builtInZoomControls = false
            domStorageEnabled = true
            allowFileAccess = true
        }
        binding.termsContents.loadUrl(url)
    }
}