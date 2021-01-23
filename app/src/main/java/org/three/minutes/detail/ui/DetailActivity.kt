package org.three.minutes.detail.ui

import android.content.Intent
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
import org.three.minutes.profile.ui.OtherProfileActivity

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


        mBinding.viewmodel = mViewModel

        setTextSizeButton()
        setLikeListener()
        setScrapListener()

        mBinding.profileId.setOnClickListener {
            val intent = Intent(this,OtherProfileActivity::class.java)
            intent.putExtra("nickName",mViewModel.detailData.value?.nickName ?: "")
            startActivity(intent)
        }

    }

    private fun setScrapListener() {
        mBinding.detailPutBtn.setOnClickListener {
            // 이미 스크랩이 되어 있는 상태
            if (mViewModel.isScrap){
                mViewModel.callUnScrap(this)
            }
            else{ // 스크랩이 안되어 있는 상태
                mViewModel.callScrap(this)
            }
        }
    }

    private fun setLikeListener() {
        mBinding.detailLikeTxt.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                mViewModel.callLike(this)
            }
            else{
                mViewModel.callUnLike(this)
            }
        }
    }

    private fun setTextSizeButton() {
        mBinding.detailFontSizeGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.size_from_minus_to_plus -> {
                    mBinding.detailContentsTxt.textSize = 18f
                }
                R.id.size_from_plus_to_minus -> {
                    mBinding.detailContentsTxt.textSize = 16f
                }
            }
        }
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