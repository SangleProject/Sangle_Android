package org.three.minutes.writing.ui


import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import kotlinx.android.synthetic.main.activity_writing_result.*
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.badge.ui.OpenedBadgePopup
import org.three.minutes.databinding.ActivityWritingResultBinding
import org.three.minutes.home.ui.HomeActivity
import org.three.minutes.word.ui.WordActivity
import org.three.minutes.writing.data.BadgeData
import org.three.minutes.writing.viewmodel.WritingResultViewModel

class WritingResultActivity : AppCompatActivity() {

    private val mBinding: ActivityWritingResultBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_writing_result)
    }
    private val mViewModel: WritingResultViewModel by viewModels()

    private val EDIT_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_writing_result)
        mBinding.apply {
            lifecycleOwner = this@WritingResultActivity
            viewModel = mViewModel
        }

        setSupportActionBar(result_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        observeViewModel()

        mViewModel.contents.value = intent.getStringExtra("contents")
        mViewModel.topic.value = intent.getStringExtra("topic")
        mViewModel.contentsCount.value = mViewModel.contents.value?.length ?: 0

        mViewModel.getCurrentTime()

        ThreeApplication.getInstance().getDataStore().token.asLiveData()
            .observe(this@WritingResultActivity,
                {
                    mViewModel.token = it
                    mViewModel.postWriting()
                })


        // 글 내용 TextView 스크롤
        mBinding.resultContentsTxt.movementMethod = ScrollingMovementMethod()

        // 좌측 상단 완료 버튼
        mBinding.resultDone.setOnClickListener {
            // 1이면 공개 0이면 비공개
            val isOpen = if (mBinding.resultOpenSwitch.isChecked) {
                1
            } else {
                0
            }
            mViewModel.putOpenWriting(isOpen)

        }
        // 삭제하기 버튼
        mBinding.resultDeleteTxt.setOnClickListener {
            mViewModel.deleteWriting()
        }
        // 수정하기 버튼
        mBinding.resultModifyTxt.setOnClickListener {
            startEditActivity()
        }

        // 다른 사람 글 둘러보기 버튼
        mBinding.resultGoWordBtn.setOnClickListener {
            val intent = Intent(this,WordActivity::class.java)
            intent.putExtra("topic", mViewModel.topic.value!!)
            startActivity(intent)
        }

    }

    @Suppress("UNCHECKED_CAST")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_CODE && resultCode == RESULT_OK) {
            mViewModel.contents.value = data?.getStringExtra("contents")

            val badgeList = data?.getSerializableExtra("badgeList") as? ArrayList<BadgeData>
            if (badgeList != null) {
                showPopUp(badgeList)
            }
        }

    }

    private fun startEditActivity() {
        val intent = Intent(this, WritingEditActivity::class.java)
        intent.putExtra("topic", mViewModel.topic.value)
        intent.putExtra("contents", mViewModel.contents.value)
        intent.putExtra("postIdx", mViewModel.postIdx)
        startActivityForResult(intent, EDIT_CODE)
    }

    // 완료 버튼, 삭제 버튼, 뒤로가기 버튼 클릭 시 호출
    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finishAndRemoveTask()
    }

    private fun observeViewModel() {
        mViewModel.badgeList.observe(this, {
            if (it.isNotEmpty()) {
                showPopUp(it as ArrayList)
            }
        })

        mViewModel.isDelete.observe(this, {
            if (it) {
                startHomeActivity()
            }
        })

        mViewModel.isDone.observe(this, {
            if (it) {
                startHomeActivity()
            }
        })
    }

    private fun showPopUp(badge: ArrayList<BadgeData>?) {
        if (badge!!.isEmpty()) {
            return
        } else {
            val popUp = OpenedBadgePopup(this, badge[0])
            badge.removeAt(0)
            popUp.setListener(object : OpenedBadgePopup.OnCloseListener {
                override fun closePopUp(v: Dialog) {
                    v.dismiss()
                    showPopUp(badge)
                }
            })
            popUp.show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startHomeActivity()
    }
}