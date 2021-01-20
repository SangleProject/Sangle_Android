package org.three.minutes.home.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.*
import org.three.minutes.R
import org.three.minutes.databinding.FragmentCalenderBinding
import org.three.minutes.home.adapter.CalendarAdapter
import org.three.minutes.home.viewmodel.HomeViewModel
import kotlin.coroutines.CoroutineContext


class CalenderFragment : Fragment(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var mBinding: FragmentCalenderBinding
    private val mViewModel: HomeViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("Home", "Calendar onAttach()")

    }


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        Log.e("Home", "Calendar onCreatedView()")


        mBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_calender, container, false)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        val calendarAdp = CalendarAdapter(
            mBinding.root.context,
            mViewModel
        )

        mViewModel.isCalendarComplete.observe(viewLifecycleOwner, {
            if (it) {
                mBinding.rcvCalendar.adapter = calendarAdp
                calendarAdp.notifyDataSetChanged()
            }
        })

        mViewModel.weekProgress.observe(viewLifecycleOwner, {
            mBinding.calendarProgress.setProgressCompat(it, true)
        })

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.calenderLeftBtn.setOnClickListener {
            mViewModel.setInitialCalendarData(-1)
        }
        mBinding.calenderRightBtn.setOnClickListener {
            mViewModel.setInitialCalendarData(1)
        }
    }


    override fun onResume() {
        super.onResume()
        mViewModel.callWeekComplete()
    }

    override fun onPause() {
        super.onPause()
        mViewModel.weekProgress.value = 0
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


}