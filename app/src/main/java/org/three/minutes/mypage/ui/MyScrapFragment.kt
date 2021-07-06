package org.three.minutes.mypage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.modal_bottom_sheet.*
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.FragmentMyScrapBinding
import org.three.minutes.databinding.FragmentMyWritingBinding
import org.three.minutes.mypage.viewmodel.MyPageViewModel


class MyScrapFragment : Fragment() {

    private lateinit var mBinding: FragmentMyScrapBinding
    private val mViewModel: MyPageViewModel by activityViewModels()
    private val bottomSheet: BottomSheetDialog by lazy {
        BottomSheetDialog(mBinding.root.context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_scrap, container, false)

        mBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }
        setObserve()
        setBottomSheet()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.myScrapFilterTxt.setOnClickListener {
            bottomSheet.show()
        }
    }

    private fun setObserve() {
        mViewModel.scrapFilter.observe(viewLifecycleOwner,{
            if (it == "최신순"){
                mViewModel.setMyScrapRecent()
            }
            else{
                mViewModel.setMyScrapPopular()
            }
        })
    }

    private fun setBottomSheet() {
        bottomSheet.setContentView(R.layout.modal_bottom_sheet)
        when(mViewModel.filter.value){
            "최신순" -> {
                checkRecent()
                mViewModel.setMyScrapRecent()
            }
            "인기순" -> {
                checkPopular()
                mViewModel.setMyScrapPopular()
            }
        }
        bottomSheet.recent_box.setOnClickListener {
            checkRecent()
            mViewModel.scrapFilter.value = "최신순"
            bottomSheet.dismiss()
        }
        bottomSheet.popular_box.setOnClickListener {
            checkPopular()
            mViewModel.scrapFilter.value = "인기순"
            bottomSheet.dismiss()
        }
        bottomSheet.close_bottom_sheet_btn.setOnClickListener {
            bottomSheet.dismiss()
        }
    }

    private fun checkRecent(){
        ThreeApplication.getInstance()
            .changeTextColor(
                blueText = bottomSheet.recent_txt,
                blackText = bottomSheet.popular_txt
            )

        ThreeApplication.getInstance().changeVisibleImage(
            showImage = bottomSheet.recent_check_img,
            hideImage = bottomSheet.popular_check_img
        )
    }

    private fun checkPopular(){
        ThreeApplication.getInstance()
            .changeTextColor(
                blueText = bottomSheet.popular_txt,
                blackText = bottomSheet.recent_txt
            )

        ThreeApplication.getInstance().changeVisibleImage(
            showImage = bottomSheet.popular_check_img,
            hideImage = bottomSheet.recent_check_img
        )
    }

}