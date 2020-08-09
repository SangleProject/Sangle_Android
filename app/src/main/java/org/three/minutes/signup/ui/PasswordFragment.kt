package org.three.minutes.signup.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_password.*

import org.three.minutes.R
import org.three.minutes.databinding.FragmentPasswordBinding
import org.three.minutes.signup.viewmodel.SignUpViewModel



class PasswordFragment : Fragment() {
    private lateinit var mContext: Context
    private lateinit var mViewModel: SignUpViewModel
    private lateinit var mImm: InputMethodManager
    private lateinit var mBinding: FragmentPasswordBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mViewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_password, container, false)

        mBinding.apply {
            lifecycleOwner = this@PasswordFragment
            viewModel = mViewModel
        }

        val view = mBinding.root

        mImm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        password_layout.setOnClickListener {
            if (mImm.isAcceptingText) {
                password_edt.clearFocus()
                password_check_edt.clearFocus()
                mImm.hideSoftInputFromWindow(password_edt.windowToken, 0)
            }
        }
    }

}