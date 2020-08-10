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
import kotlinx.android.synthetic.main.fragment_info.*
import org.three.minutes.R
import org.three.minutes.databinding.FragmentInfoBinding
import org.three.minutes.signup.viewmodel.SignUpViewModel


class InfoFragment : Fragment() {

    private lateinit var mViewModel: SignUpViewModel
    private lateinit var mImm: InputMethodManager
    private lateinit var mActivity : SignupActivity
    private lateinit var mBinding : FragmentInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as SignupActivity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mImm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mViewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_info,container,false)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        mBinding.ageEdt.clearFocus()

        //다음 단계로 가기 위한 조건
        //나이 항목을 입력하고, 성별 체크까지 마쳤을 경우
       mViewModel.age.observe(this, Observer<String> { age ->
            mActivity.signup_next_txt.isEnabled = !age.isNullOrBlank() && mViewModel.gender.value != 0
        })

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //키보드 내리고 포커스 해제
        info_layout.setOnClickListener {
            age_edt.clearFocus()
            mImm.hideSoftInputFromWindow(view.windowToken,0)
        }

        //성별 남 여 확인
        gender_rg.setOnCheckedChangeListener { _, buttonId ->
            when(buttonId){
                R.id.man_rb -> {
                    mViewModel.gender.value = 1
                }

                R.id.woman_rb -> {
                    mViewModel.gender.value = 2
                }
            }
        }

    }

}