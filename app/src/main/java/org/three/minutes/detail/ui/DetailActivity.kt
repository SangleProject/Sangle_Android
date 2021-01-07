package org.three.minutes.detail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityDetailBinding
import org.three.minutes.detail.viewmodel.DetailOtherViewModel
import org.three.minutes.home.data.ResponseFameData

class DetailActivity : AppCompatActivity() {
    private val mBinding : ActivityDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }
    private val mViewModel : DetailOtherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@DetailActivity
        }
        setSupportActionBar(mBinding.detailToolbar)

        setToolbarIcon()

        mViewModel.getToken.observe(this,{
            mViewModel.token = it
        })
        getIntentData()
        mViewModel.callOtherDetailData()

        // 팩토리 패턴을 이용해서 파라미터 값 생성
        mBinding.viewmodel = mViewModel

    }

    private fun getIntentData() {

        val postIdx = intent.getIntExtra("postIdx",-1)
        mViewModel.postIdx = postIdx
    }

    private fun setToolbarIcon() {
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_detail_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            // go back
            android.R.id.home -> {
                finish()
            }
            // click share
            R.id.action_share -> {
                Toast.makeText(this, "Share",Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}