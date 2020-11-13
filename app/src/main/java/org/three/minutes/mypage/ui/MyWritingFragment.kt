package org.three.minutes.mypage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.modal_bottom_sheet.*
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.FragmentMyWritingBinding
import org.three.minutes.mypage.data.MyWritingData
import org.three.minutes.mypage.viewmodel.MyPageViewModel
import org.three.minutes.util.changeBlackColor
import org.three.minutes.util.changeBlueColor


class MyWritingFragment : Fragment() {

    private lateinit var mBinding: FragmentMyWritingBinding
    private val mViewModel: MyPageViewModel by activityViewModels()
    private val bottomSheet: BottomSheetDialog by lazy {
        BottomSheetDialog(mBinding.root.context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_writing, container, false)
        mBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }
        setBottomSheet()

        setMyWritingData()

        return mBinding.root
    }

    private fun setBottomSheet() {
        bottomSheet.setContentView(R.layout.modal_bottom_sheet)
        when(mViewModel.filter.value){
            "최신순" -> {
                checkRecent()
            }
            "인기순" -> {
                checkPopular()
            }
        }
        bottomSheet.recent_box.setOnClickListener {
            checkRecent()
            mViewModel.filter.value = "최신순"
            bottomSheet.dismiss()
        }
        bottomSheet.popular_box.setOnClickListener {
            checkPopular()
            mViewModel.filter.value = "인기순"
            bottomSheet.dismiss()
        }
        bottomSheet.close_bottom_sheet_btn.setOnClickListener {
            bottomSheet.dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.myWritingFilterTxt.setOnClickListener {
            bottomSheet.show()
        }
    }

    private fun setMyWritingData() {
        mViewModel.myWritingList = mutableListOf(
            MyWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224
            ),
            MyWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224
            ),
            MyWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224
            ),
            MyWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224
            ),
            MyWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224
            ),
            MyWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224
            )
        )
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