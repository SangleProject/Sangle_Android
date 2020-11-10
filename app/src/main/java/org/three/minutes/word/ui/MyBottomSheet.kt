package org.three.minutes.word.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.three.minutes.R
import org.three.minutes.databinding.MyWritingModalBottomSheetBinding
import org.three.minutes.word.viewmodel.WordViewModel

class MyBottomSheet : BottomSheetDialogFragment(){
    private var _binding : MyWritingModalBottomSheetBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel : WordViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MyWritingModalBottomSheetBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.recentBox.setOnClickListener {
            changeTextColorBlue(mBinding.recentTxt)
            changeTextColorBlack(mBinding.popularTxt)
            dismiss()
        }
        mBinding.popularBox.setOnClickListener {
            changeTextColorBlue(mBinding.popularTxt)
            changeTextColorBlack(mBinding.recentTxt)
            dismiss()
        }
    }

    private fun changeTextColorBlue(view : TextView){
        view.setTextColor(ContextCompat.getColor(mBinding.root.context, R.color.main_blue))
    }

    private fun changeTextColorBlack(view : TextView){
        view.setTextColor(ContextCompat.getColor(mBinding.root.context, R.color.black_60))
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}