package org.three.minutes.signup.ui.claus

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_claus.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityClausBinding
import org.three.minutes.signup.viewmodel.ClausViewModel

class ClausActivity : AppCompatActivity() {
    private val mViewModel : ClausViewModel by viewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityClausBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_claus)
        val title = intent.getStringExtra("title")
        mViewModel.layoutTitle.value = title
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        claus_web.webViewClient = WebViewClient()
        //클릭 시 새창이 안뜨게

        claus_web.settings.apply {
            javaScriptEnabled = true // 자바 스크립트 허용 여부
//            useWideViewPort = true // 화면 사이즈 맞추기 혀용 여부
            setSupportZoom(false) // 화면 줌 허용 여부
            builtInZoomControls = false // 화면 확대 축소 허용 여부
            cacheMode = WebSettings.LOAD_NO_CACHE // 브라우저 캐시 허용 여부
            allowFileAccess = true
        }



//        val googleDocs = "https://docs.google.com/gview?embedded=true&url="

        when(mViewModel.layoutTitle.value){
            "개인정보보호 정책" -> {
                claus_web.loadUrl("")
            }
            "서비스 이용약관" -> {
                claus_web.loadUrl("https://drive.google.com/file/d/1IpZhJX0DUTTqmOqVWET_flIggbV3rz6Y/view")
            }
        }

        claus_back_img.setOnClickListener {
            finish()
        }
    }
}