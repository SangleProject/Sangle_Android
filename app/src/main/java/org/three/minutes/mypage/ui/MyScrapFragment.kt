package org.three.minutes.mypage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import org.three.minutes.R
import org.three.minutes.databinding.FragmentMyScrapBinding
import org.three.minutes.databinding.FragmentMyWritingBinding
import org.three.minutes.mypage.viewmodel.MyPageViewModel


class MyScrapFragment : Fragment() {

    private lateinit var mBinding: FragmentMyScrapBinding
    private val mViewModel: MyPageViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_scrap, container, false)

        mBinding.apply {
            viewModel = mViewModel
        }
        return mBinding.root
    }

}