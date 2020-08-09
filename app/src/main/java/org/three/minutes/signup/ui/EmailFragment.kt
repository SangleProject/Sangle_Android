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
import kotlinx.android.synthetic.main.fragment_email.*
import org.three.minutes.R
import org.three.minutes.databinding.FragmentEmailBinding
import org.three.minutes.signup.viewmodel.SignUpViewModel



class EmailFragment : Fragment() {

    private lateinit var mContext : Context
    private lateinit var mViewModel : SignUpViewModel
    private lateinit var mImm : InputMethodManager
    private lateinit var mBinding : FragmentEmailBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mViewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val mView = inflater.inflate(R.layout.fragment_email, container, false)
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_email,container,false)
        val mView = mBinding.root

        mBinding.apply {
            lifecycleOwner = this@EmailFragment
            viewModel = mViewModel
        }
        mImm = mView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email_layout.setOnClickListener {
            if(mImm.isAcceptingText) {
                mBinding.emailLayout.clearFocus()
                mImm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

}