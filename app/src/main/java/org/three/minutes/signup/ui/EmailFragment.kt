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
import org.three.minutes.util.keyBoardObserve


class EmailFragment : Fragment() {

    private lateinit var mActivity: SignupActivity
    private lateinit var mContext : Context
    private lateinit var mViewModel : SignUpViewModel
    private lateinit var mImm : InputMethodManager
    private lateinit var mBinding : FragmentEmailBinding

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
//        val mView = inflater.inflate(R.layout.fragment_email, container, false)
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_email,container,false)
        val mView = mBinding.root

        mBinding.apply {
            lifecycleOwner = this@EmailFragment
            viewModel = mViewModel
            fragment = this@EmailFragment
        }

        mActivity.keyBoardObserve {
            email_edt.clearFocus()
        }

        mImm = mActivity.getMethodManager()

        return mView
    }

    fun hideKeyBoard(){
        if(mImm.isAcceptingText){
            mImm.hideSoftInputFromWindow(view?.windowToken,0)
        }
    }

}