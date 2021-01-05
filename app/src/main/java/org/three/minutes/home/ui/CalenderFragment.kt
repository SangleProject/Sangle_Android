package org.three.minutes.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import org.three.minutes.R
import org.three.minutes.databinding.FragmentCalenderBinding
import org.three.minutes.home.adapter.CalendarAdapter
import org.three.minutes.home.viewmodel.HomeViewModel
import java.util.*
import kotlin.coroutines.CoroutineContext


class CalenderFragment : Fragment(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var mBinding: FragmentCalenderBinding


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_calender, container, false)
        mBinding.lifecycleOwner = this

        val mViewModel: HomeViewModel by activityViewModels()
        mBinding.viewModel = mViewModel

        val calendarAdp = CalendarAdapter(
            mBinding.root.context,
            mViewModel
        )

        mBinding.rcvCalendar.adapter = calendarAdp
        calendarAdp.notifyDataSetChanged()


        return mBinding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


}