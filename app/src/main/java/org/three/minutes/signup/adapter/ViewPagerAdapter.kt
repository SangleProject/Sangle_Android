package org.three.minutes.signup.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.three.minutes.signup.ui.EmailFragment
import org.three.minutes.signup.ui.PasswordFragment

class ViewPagerAdapter (fm : FragmentManager) : FragmentStatePagerAdapter(fm,
BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> EmailFragment()
            1-> PasswordFragment()
            else->EmailFragment()
        }
    }

    override fun getCount(): Int = 2
}