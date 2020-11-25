package org.three.minutes.word.ui

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
import org.three.minutes.databinding.FragmentSearchResultBinding
import org.three.minutes.mypage.data.MyWritingData
import org.three.minutes.word.data.SearchWritingData
import org.three.minutes.word.viewmodel.WordViewModel


class SearchResultFragment : Fragment() {

    private lateinit var mBinding: FragmentSearchResultBinding
    private val mViewModel : WordViewModel by activityViewModels()
    private val bottomSheet: BottomSheetDialog by lazy {
        BottomSheetDialog(mBinding.root.context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_search_result,
            container, false
        )
        mBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }

        setBottomSheet()
        setSearchList()

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.searchWritingFilterTxt.setOnClickListener {
            bottomSheet.show()
        }
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


    private fun setSearchList() {
        mViewModel.searchList = listOf(
            SearchWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224,
                userName = "창의적인 똑똑이"
            ),
            SearchWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224,
                userName = "창의적인 똑똑이"
            ),
            SearchWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224,
                userName = "창의적인 똑똑이"
            ),
            SearchWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224,
                userName = "창의적인 똑똑이"
            ),
            SearchWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224,
                userName = "창의적인 똑똑이"
            ),
            SearchWritingData(
                title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224,
                userName = "창의적인 똑똑이"
            )
        )
    }

}