package org.three.minutes.signup.ui

import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_password.*
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.FragmentPasswordBinding
import org.three.minutes.signup.viewmodel.SignUpViewModel
import org.three.minutes.singleton.PatternObject


class PasswordFragment : Fragment() {
    private lateinit var mViewModel: SignUpViewModel
    private lateinit var mImm: InputMethodManager
    private lateinit var mBinding: FragmentPasswordBinding
    private lateinit var mActivity : SignupActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as SignupActivity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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

        mImm = ThreeApplication.getInstance().getInputMethodManager()

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

        //패스워드 조건 observe
        mViewModel.password.observe(viewLifecycleOwner, Observer<String> { password ->
            val m = PatternObject.pPattern.matcher(password)
            if (!password.isNullOrBlank()) {
                if (m.matches()) {
                    password_error_txt.visibility = View.INVISIBLE
                    img_password_check.visibility = View.VISIBLE
                } else {
                    password_error_txt.visibility = View.VISIBLE
                    img_password_check.visibility = View.INVISIBLE
                }
            }
            else {
                password_error_txt.visibility = View.INVISIBLE
                img_password_check.visibility = View.INVISIBLE
            }
        })

        //패스워드 체크 조건 observe
        mViewModel.passwordCheck.observe(viewLifecycleOwner, Observer<String> { passwordCheck ->
            if (!passwordCheck.isNullOrBlank()) {

                if (passwordCheck == mViewModel.password.value) {
                    mActivity.signup_next_txt.isEnabled = true
                    password_check_error_txt.visibility = View.INVISIBLE
                    img_password_check.visibility = View.VISIBLE
                } else {
                    mActivity.signup_next_txt.isEnabled = false
                    password_check_error_txt.visibility = View.VISIBLE
                    img_password_check.visibility = View.INVISIBLE
                }
            }
            else {
                mActivity.signup_next_txt.isEnabled = false
                password_check_error_txt.visibility = View.INVISIBLE
                img_password_check.visibility = View.INVISIBLE
            }
        })

    }

}