package org.three.minutes.mypage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.three.minutes.mypage.ui.MyWritingFragment
import org.three.minutes.signup.adapter.ViewPagerAdapter
import java.lang.IllegalStateException

class MyViewPagerAdapter (fm : FragmentManager) : FragmentStatePagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> MyWritingFragment()
            1 -> MyWritingFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

    override fun getCount(): Int = 2

}