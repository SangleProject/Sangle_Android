package org.three.minutes.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.three.minutes.databinding.FragmentOnBoarding2Binding


class OnBoardingFragment2 : Fragment() {


    private var _binding: FragmentOnBoarding2Binding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOnBoarding2Binding.inflate(inflater,container,false)
        return binding.root    }

}