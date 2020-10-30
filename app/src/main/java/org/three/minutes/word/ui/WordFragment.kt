package org.three.minutes.word.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import org.three.minutes.R
import org.three.minutes.databinding.FragmentWordBinding
import org.three.minutes.word.data.TodayWordData
import org.three.minutes.word.viewmodel.WordViewModel


class WordFragment : Fragment() {
    private lateinit var mBinding : FragmentWordBinding
    private val mViewModel : WordViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_word, container,false)
        mBinding.viewModel = mViewModel
        mViewModel.todayWordList = mutableListOf(
            TodayWordData("바다",16,true),
            TodayWordData("바다",16,false),
            TodayWordData("바다",16,false)
        )

        return mBinding.root
    }

}