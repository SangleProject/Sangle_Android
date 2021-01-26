package org.three.minutes.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.three.minutes.databinding.FragmentOnBoarding1Binding


class OnBoardingFragment1 : Fragment() {

    private var _binding: FragmentOnBoarding1Binding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoarding1Binding.inflate(inflater,container,false)
        return binding.root
    }
}