package org.three.minutes.detail.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityDetailMyBinding
import org.three.minutes.detail.viewmodel.DetailViewModel
import org.three.minutes.util.showToast
import org.three.minutes.writing.ui.WritingEditActivity

class DetailMyActivity : AppCompatActivity() {
    private val EDIT_CODE = 101
    private val mBinding: ActivityDetailMyBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_detail_my)
    }

    private val mViewModel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@DetailMyActivity
        }

        setSupportActionBar(mBinding.myToolbar)

        setToolbarIcon()

        getIntentData()
        mViewModel.getToken.observe(this, {
            mViewModel.token = it
        })
        mViewModel.isDelete.observe(this, {
            if (it) {
                showToast("글을 삭제했어요!")
                finish()
            }
        })
        mViewModel.callMyDetailData()
        mBinding.viewmodel = mViewModel

        setLikeListener()
        setTextSizeButton()
        mBinding.myWordOpenSwitch.setOnCheckedChangeListener { _, isChecked ->
            mViewModel.detailData.value?.open = isChecked
        }

    }

    private fun setLikeListener() {
        mBinding.myLikeTxt.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                if (mViewModel.likeFirst){
                    mViewModel.likeFirst = false
                }
                else{
                    mViewModel.callLike(this)
                }
            }
            else{
                mViewModel.callUnLike(this)
            }
        }
    }

    private fun setTextSizeButton() {
        mBinding.detailFontSizeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.size_from_minus_to_plus -> {
                    mBinding.myContentsTxt.textSize = 18f
                }
                R.id.size_from_plus_to_minus -> {
                    mBinding.myContentsTxt.textSize = 16f
                }
            }
        }
    }

    private fun getIntentData() {
        val postIdx = intent.getIntExtra("postIdx", -1)
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
        menuInflater.inflate(R.menu.toolbar_detail_my_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_share -> {
                Toast.makeText(this, "click Share", Toast.LENGTH_SHORT).show()
            }
            R.id.action_configu -> {
                val intent = Intent(this, WritingEditActivity::class.java)
                intent.putExtra("topic", mViewModel.detailData.value!!.topic)
                intent.putExtra("contents", mViewModel.detailData.value!!.postWrite)
                intent.putExtra("postIdx", mViewModel.detailData.value!!.postIdx)
                startActivityForResult(intent, EDIT_CODE)
            }
            R.id.action_delete -> {
                mViewModel.callDeleteData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_CODE && resultCode == RESULT_OK) {
            // 수정 성공
            Log.e("DetailMyActivity", "Modify Ok")
            showToast("글을 수정했어요~")
            finish()
        }
    }
}