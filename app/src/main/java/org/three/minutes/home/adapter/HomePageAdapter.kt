package org.three.minutes.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import org.three.minutes.home.ui.CalenderFragment
import org.three.minutes.home.ui.FeedFragment
import org.three.minutes.home.ui.ProfileFragment

class HomePageAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm,
BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0->FeedFragment()
            1->ProfileFragment()
            else -> CalenderFragment()
        }
    }

    override fun getCount(): Int = 3
}