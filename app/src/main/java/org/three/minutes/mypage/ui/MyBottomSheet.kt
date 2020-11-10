package org.three.minutes.mypage.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.three.minutes.R
import org.three.minutes.databinding.MyWritingModalBottomSheetBinding
import org.three.minutes.mypage.viewmodel.MyPageViewModel

class MyBottomSheet : BottomSheetDialogFragment() {
    private var _binding: MyWritingModalBottomSheetBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel: MyPageViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MyWritingModalBottomSheetBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filter = mViewModel.filter.value.toString()
        setBottomDialog(filter)
        mBinding.recentBox.setOnClickListener {
            if (mViewModel.filter.value.toString() == "인기순") {
                mViewModel.filter.value = "최신순"
            }
            dialog?.dismiss()
        }
        mBinding.popularBox.setOnClickListener {
            if (mViewModel.filter.value.toString() == "최신순") {
                mViewModel.filter.value = "인기순"
            }
            dialog?.dismiss()
        }
        mBinding.closeBottomSheetBtn.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun setBottomDialog(filter: String) {
        when(filter){
            "최신순" -> {
                changeTextColorBlue(mBinding.recentTxt)
                changeTextColorBlack(mBinding.popularTxt)
                showCheckImg(mBinding.recentCheckImg)
            }
            "인기순" -> {
                changeTextColorBlue(mBinding.popularTxt)
                changeTextColorBlack(mBinding.recentTxt)
                showCheckImg(mBinding.popularCheckImg)
            }
        }
    }

    private fun changeTextColorBlue(view: TextView) {
        view.setTextColor(ContextCompat.getColor(mBinding.root.context, R.color.main_blue))
    }

    private fun changeTextColorBlack(view: TextView) {
        view.setTextColor(ContextCompat.getColor(mBinding.root.context, R.color.black_60))
    }

    private fun showCheckImg(view : ImageView){
        view.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}