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
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import org.three.minutes.R
import org.three.minutes.databinding.FragmentCalenderBinding
import org.three.minutes.home.viewmodel.HomeViewModel
import java.util.*


class CalenderFragment : Fragment() {

    private lateinit var mBinding: FragmentCalenderBinding


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_calender, container, false)

        val mViewModel : HomeViewModel by activityViewModels()

        mBinding.viewModel = mViewModel

        CoroutineScope(Default).launch {
            val cal = Calendar.getInstance() // 현재 날짜 요일 및 시간
            Log.d("ShowDate","${cal.get(Calendar.YEAR)}년 ${cal.get(Calendar.MONTH) + 1}월 ${cal.get(Calendar.DATE)}일")
        }

        return mBinding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}