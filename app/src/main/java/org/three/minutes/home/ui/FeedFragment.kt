package org.three.minutes.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import org.three.minutes.R
import org.three.minutes.databinding.FragmentFeedBinding
import org.three.minutes.home.adapter.FeedRcvAdapter
import org.three.minutes.home.data.FeedData
import org.three.minutes.util.LinePagerIndicatorDecoration
import org.three.minutes.util.RcvItemDeco


class FeedFragment : Fragment() {

    private lateinit var mBinding : FragmentFeedBinding
    private lateinit var mAdapter : FeedRcvAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_feed,container,false)
        setFeedRcv()
        return mBinding.root
    }

    private fun setFeedRcv() {
        mAdapter = FeedRcvAdapter(mBinding.root.context)
        val snapHelper = PagerSnapHelper()
        mBinding.feedRcv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(mBinding.root.context
                , LinearLayoutManager.HORIZONTAL
                , false)
            addItemDecoration(LinePagerIndicatorDecoration(mBinding.root.context))
        }
        snapHelper.attachToRecyclerView(mBinding.feedRcv)
        mAdapter.data = listOf(
            FeedData("빨대", "2020.06.22 (월) PM 2:30", "글감 내용","창의적인 똑똑씨",1224),
            FeedData("빨대", "2020.06.22 (월) PM 2:30","글감 내용","창의적인 똑똑씨",1224),
            FeedData("빨대", "2020.06.22 (월) PM 2:30","글감 내용","창의적인 똑똑씨",1224)
            )

        mAdapter.notifyDataSetChanged()
    }

}