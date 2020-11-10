package org.three.minutes.mypage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.FragmentMyWritingBinding
import org.three.minutes.mypage.data.MyWritingData
import org.three.minutes.mypage.viewmodel.MyPageViewModel


class MyWritingFragment : Fragment() {

    private lateinit var mBinding : FragmentMyWritingBinding
    private val mViewModel : MyPageViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_writing,container,false)
        mBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }

        setMyWritingData()

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.myWritingFilterTxt.setOnClickListener {
            val activity = activity as MyPageActivity
            activity.showBottomSheet()
        }
    }

    private fun setMyWritingData() {
        mViewModel.myWritingList = mutableListOf(
            MyWritingData(title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224),
            MyWritingData(title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224),
            MyWritingData(title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224),
            MyWritingData(title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224),
            MyWritingData(title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224),
            MyWritingData(title = "빨대", date = getString(R.string.date_sample),
                contents = getString(R.string.contents_sample), favoriteCount = 1224)
        )
    }

}