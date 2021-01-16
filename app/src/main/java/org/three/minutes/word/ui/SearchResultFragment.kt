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
import org.three.minutes.word.viewmodel.WordViewModel


class SearchResultFragment : Fragment() {

    private lateinit var mBinding: FragmentSearchResultBinding
    private val mViewModel : WordViewModel by activityViewModels()

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
        return mBinding.root
    }

    private fun setFilterView() {
        // 상단 filter 부분 클릭 리스너 설정
        mBinding.searchResultFilter.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.search_result_topic -> {
                    // change topic filter
                }
                R.id.search_result_contents -> {
                    // change topic contents
                }
                R.id.search_result_user -> {
                    // change topic users
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}