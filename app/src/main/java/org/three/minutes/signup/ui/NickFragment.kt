package org.three.minutes.signup.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.fragment_nick.*
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.FragmentNickBinding
import org.three.minutes.signup.viewmodel.SignUpViewModel


class NickFragment : Fragment() {

    private lateinit var mBinding : FragmentNickBinding
    private lateinit var mActivity: SignupActivity
    private lateinit var mViewModel : SignUpViewModel
    private lateinit var mImm : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as SignupActivity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mImm = ThreeApplication.getInstance().getInputMethodManager()
        mViewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_nick,container,false)
        mBinding.apply {
            lifecycleOwner = this@NickFragment
            viewModel = mViewModel
        }

        mViewModel.nickname.observe(viewLifecycleOwner, Observer<String> {nickname ->
            mActivity.signup_next_txt.isEnabled = !nickname.isNullOrBlank() && nickname.length >=2
        })

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nick_layout.setOnClickListener {
            nick_edt.clearFocus()
            mImm.hideSoftInputFromWindow(view.windowToken,0)
        }

        //개인정보 글씨 눌렀을 때
        guard_txt.setOnClickListener {
            makeIntentAndStart("개인정보보호 정책")
        }

        //이용약관 글씨 눌렀을 때
        service_txt.setOnClickListener {
            makeIntentAndStart("서비스 이용약관")
        }
    }

    private fun makeIntentAndStart(title : String){
        val intent = Intent(mActivity,
           TermsActivity::class.java)
        intent.putExtra("title",title)
        startActivity(intent)
    }

}