package org.three.minutes.home.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.FragmentFeedBinding
import org.three.minutes.home.adapter.FeedRcvAdapter
import org.three.minutes.home.data.FeedData
import org.three.minutes.home.viewmodel.HomeViewModel
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.*
import kotlin.coroutines.CoroutineContext


class FeedFragment : Fragment(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var mBinding: FragmentFeedBinding
    private lateinit var mAdapter: FeedRcvAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_feed, container, false)
        val mViewModel: HomeViewModel by activityViewModels()
        mBinding.viewModel = mViewModel

        setFeedRcv()

        mViewModel.isFameComplete.observe(viewLifecycleOwner, {
            if (it) {
                if (mViewModel.fameDataList.value!!.isNullOrEmpty()) {
                    Log.i("fame", "fame is empty")
                    mBinding.layoutEmptyFeed.visibility = View.VISIBLE
                    mBinding.feedRcv.visibility = View.GONE
                } else {
                    Log.i("fame", "fame is not empty")
                    mBinding.layoutEmptyFeed.visibility = View.GONE
                    mBinding.feedRcv.visibility = View.VISIBLE
                    mAdapter.data = mViewModel.fameDataList.value!!
                    mAdapter.notifyDataSetChanged()
                }
            } else {
                mBinding.layoutEmptyFeed.visibility = View.VISIBLE
                mBinding.feedRcv.visibility = View.GONE
            }
        })

        return mBinding.root
    }

    private fun setFeedRcv() {
        mAdapter = FeedRcvAdapter(mBinding.root.context)
        val snapHelper = PagerSnapHelper()
        mBinding.feedRcv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(
                mBinding.root.context, LinearLayoutManager.HORIZONTAL, false
            )
            addItemDecoration(LinePagerIndicatorDecoration(mBinding.root.context))
            touchControl { state ->
                (activity as HomeActivity).isEnableSwipeRefresh(state == RecyclerView.SCROLL_STATE_IDLE)
            }
        }
        snapHelper.attachToRecyclerView(mBinding.feedRcv)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}