package org.three.minutes.detail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import org.three.minutes.R
import org.three.minutes.databinding.ActivityDetailBinding
import org.three.minutes.detail.viewmodel.DetailViewModel
import org.three.minutes.detail.viewmodel.DetailViewModelFactory
import org.three.minutes.home.data.FeedData
import org.three.minutes.home.data.ResponseFameData

class DetailActivity : AppCompatActivity() {
    private val mBinding : ActivityDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }
    private lateinit var mViewModel : DetailViewModel
    private lateinit var mData : ResponseFameData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@DetailActivity
        }
        setSupportActionBar(mBinding.detailToolbar)

        setToolbarIcon()

        getIntentData()

        // 팩토리 패턴을 이용해서 파라미터 값 생성
        mViewModel = ViewModelProvider(this, DetailViewModelFactory(mData))
            .get(DetailViewModel::class.java)
        mBinding.viewmodel = mViewModel

    }

    private fun getIntentData() {
        mData = intent.getSerializableExtra("feedData") as ResponseFameData
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