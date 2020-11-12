package org.three.minutes.word.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.FragmentSearchEmptySearchBinding


class SearchEmptyFragment : Fragment() {

    private lateinit var mBinding: FragmentSearchEmptySearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_search_empty_search,
            container, false
        )

        mBinding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return mBinding.root
    }

}