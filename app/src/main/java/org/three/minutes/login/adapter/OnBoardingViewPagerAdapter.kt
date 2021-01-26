package org.three.minutes.login.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.three.minutes.login.ui.*

class OnBoardingViewPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> OnBoardingFragment1()
            1 -> OnBoardingFragment2()
            2 -> OnBoardingFragment3()
            3 -> OnBoardingFragment4()
            4 -> OnBoardingFragment5()
            5 -> OnBoardingFragment6()
            else -> OnBoardingFragment6()
        }
    }

    override fun getCount(): Int = 6

}