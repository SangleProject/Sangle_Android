package org.three.minutes.detail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import org.three.minutes.R
import org.three.minutes.databinding.ActivityDetailMyBinding
import org.three.minutes.detail.viewmodel.DetailViewModel
import org.three.minutes.home.data.ResponseFameData

class DetailMyActivity : AppCompatActivity() {
    private val mBinding : ActivityDetailMyBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_detail_my)
    }

    private val mViewModel : DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@DetailMyActivity
        }

        setSupportActionBar(mBinding.myToolbar)

        setToolbarIcon()

        getIntentData()
        mViewModel.getToken.observe(this,{
            mViewModel.token = it
        })
        mViewModel.callMyDetailData()
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
        menuInflater.inflate(R.menu.toolbar_detail_my_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
            R.id.action_share -> {
                Toast.makeText(this,"click Share",Toast.LENGTH_SHORT).show()
            }
            R.id.action_configu -> {
                Toast.makeText(this,"click Configuration",Toast.LENGTH_SHORT).show()
            }
            R.id.action_delete -> {
                Toast.makeText(this,"click Delete",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}