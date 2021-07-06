package org.three.minutes.word.ui


import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import org.three.minutes.CloseTopicPopUpListener
import org.three.minutes.R
import org.three.minutes.databinding.FragmentSearchResultBinding
import org.three.minutes.home.ui.HomeActivity
import org.three.minutes.word.viewmodel.WordViewModel


class SearchResultFragment : Fragment() {

    private lateinit var mBinding: FragmentSearchResultBinding
    private val mViewModel: WordViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_search_result,
            container, false
        )
        mBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }
        setFilterView()
        setObserve()

        return mBinding.root
    }

    private fun setObserve() {

        mViewModel.isFilterTopic.observe(viewLifecycleOwner, {check ->
            if (check) {
                mBinding.searchResultTopic.typeface = Typeface.DEFAULT_BOLD
                mViewModel.isFilterContents.value = false
                mViewModel.isFilterUser.value = false
                mViewModel.changeTopicList()
            }
            else{
                mBinding.searchResultTopic.typeface = Typeface.DEFAULT
            }
        })

        mViewModel.isFilterContents.observe(viewLifecycleOwner, {check ->
            if (check) {
                mBinding.searchResultContents.typeface = Typeface.DEFAULT_BOLD
                mViewModel.isFilterTopic.value = false
                mViewModel.isFilterUser.value = false
                mViewModel.changeContentsList()
            }
            else{
                mBinding.searchResultContents.typeface = Typeface.DEFAULT
            }
        })

        mViewModel.isFilterUser.observe(viewLifecycleOwner, {check ->
            if (check) {
                mBinding.searchResultUser.typeface = Typeface.DEFAULT_BOLD
                mViewModel.isFilterContents.value = false
                mViewModel.isFilterTopic.value = false
            }
            else{
                mBinding.searchResultUser.typeface = Typeface.DEFAULT
            }
        })
    }


    private fun setFilterView() {
        // 상단 filter 부분 클릭 리스너 설정
        mBinding.searchResultFilter.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.search_result_topic -> {
                    // change topic filter
                    mViewModel.isFilterTopic.value = true
                }
                R.id.search_result_contents -> {
                    // change topic contents
                    mViewModel.isFilterContents.value = true
                }
                R.id.search_result_user -> {
                    // change topic users
                    mViewModel.isFilterUser.value = true
                }
            }
        }
    }
}