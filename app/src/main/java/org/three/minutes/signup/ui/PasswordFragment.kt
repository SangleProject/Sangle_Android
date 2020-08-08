package org.three.minutes.signup.ui

import android.content.Context
import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import org.three.minutes.R
import org.three.minutes.databinding.FragmentPasswordBinding
import org.three.minutes.signup.viewmodel.SignUpViewModel


class PasswordFragment : Fragment() {
    private lateinit var mActivity: SignupActivity
    private lateinit var mContext : Context
    private lateinit var mViewModel : SignUpViewModel
    private lateinit var mImm : InputMethodManager
    private lateinit var mBinding : FragmentPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as SignupActivity

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mViewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_password,container,false)
        val view = mBinding.root

        mImm = mActivity.getMethodManager()
        return view
    }

}