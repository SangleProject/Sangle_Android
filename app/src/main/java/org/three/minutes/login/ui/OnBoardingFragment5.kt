package org.three.minutes.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.three.minutes.R
import org.three.minutes.databinding.FragmentOnBoarding5Binding


class OnBoardingFragment5 : Fragment() {

    private var _binding : FragmentOnBoarding5Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoarding5Binding.inflate(inflater,container,false)
        return binding.root
    }

}