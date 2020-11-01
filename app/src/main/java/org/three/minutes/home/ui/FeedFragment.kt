package org.three.minutes.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.three.minutes.R
import org.three.minutes.databinding.FragmentFeedBinding
import org.three.minutes.home.adapter.FeedRcvAdapter
import org.three.minutes.home.data.FeedData
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
        mBinding.feedRcv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(mBinding.root.context
                , LinearLayoutManager.HORIZONTAL
                , false)
            addItemDecoration(RcvItemDeco(mBinding.root.context,8))
        }

        mAdapter.data = listOf(
            FeedData("빨대", "2020.06.22 (월) PM 2:30"),
            FeedData("빨대", "2020.06.22 (월) PM 2:30"),
            FeedData("빨대", "2020.06.22 (월) PM 2:30")
            )

        mAdapter.notifyDataSetChanged()
    }

}