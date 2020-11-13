package org.three.minutes.word.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import org.three.minutes.R
import org.three.minutes.databinding.FragmentSearchResultBinding
import org.three.minutes.mypage.data.MyWritingData
import org.three.minutes.word.data.SearchWritingData
import org.three.minutes.word.viewmodel.WordViewModel


class SearchResultFragment : Fragment() {

    private lateinit var mBinding: FragmentSearchResultBinding
    private val mViewModel : WordViewModel by activityViewModels()

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

        setSearchList()

        return mBinding.root
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