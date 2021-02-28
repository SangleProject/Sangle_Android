package org.three.minutes.writing.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.badge.ui.OpenedBadgePopup
import org.three.minutes.databinding.ActivityWritingEditBinding
import org.three.minutes.singleton.PopUpObject
import org.three.minutes.writing.viewmodel.WritingEditViewModel

class WritingEditActivity : AppCompatActivity() {
    private val mViewModel : WritingEditViewModel by viewModels()
    private val mBinding : ActivityWritingEditBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_writing_edit)
    }
    private lateinit var mImm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@WritingEditActivity
            viewModel = mViewModel
        }
        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

        ThreeApplication.getInstance().getDataStore().token.asLiveData().observe(this,{
            mViewModel.token = it
        })

        // viewModel observe
        observeViewModel()

        val intent = intent

        mViewModel.topic.value =  intent.getStringExtra("topic")
        mViewModel.contents.value = intent.getStringExtra("contents")
        mViewModel.postIdx = intent.getIntExtra("postIdx",-1)

        mBinding.writingCompleteTxt.setOnClickListener {
            mViewModel.callEdit()
        }
        mBinding.writingCancleTxt.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

    }

    private fun observeViewModel() {
        mViewModel.contents.observe(this,{
            mViewModel.contentsCount.value = it.length
        })
        // 글 수정 완료시 true로 변경
        mViewModel.isEdited.observe(this,{
            if (it){
                val intent = Intent()
                intent.putExtra("contents",mViewModel.contents.value)
                if (mViewModel.badgeList.value!!.isNotEmpty()){
                    intent.putExtra("badgeList", mViewModel.badgeList.value!!.toTypedArray())
                }
                setResult(RESULT_OK,intent)
                finish()
            }
        })
//        뱃지 처리 부분 문제 있음
//        mViewModel.badgeList.observe(this,{
//            if (it.isNotEmpty()){
//                val badgeData = it[0]
//                val badgePopUp = OpenedBadgePopup(this)
//                badgePopUp.setNewPopUp(badgeData)
//                badgePopUp.setCancelClick(object : OpenedBadgePopup.SetOnClickListener{
//                    override fun onCancelClick(dialog: Dialog) {
//                        mViewModel.badgeList.value?.removeAt(0)
//                        dialog.dismiss()
//                    }
//                })
//                badgePopUp.show()
//            }
//        })
    }

    // 작성 공간 외 다른 공간 클릭 시 키보드 내리고 포커스 해제
    fun clearAllFocus(view: View) {
        if (mImm.isAcceptingText) {
            view.clearFocus()
            mImm.hideSoftInputFromWindow(mBinding.writingContentsEdt.windowToken, 0)

        }
    }
}