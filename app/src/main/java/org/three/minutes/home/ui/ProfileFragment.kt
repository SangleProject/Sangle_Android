package org.three.minutes.home.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import org.three.minutes.R
import org.three.minutes.databinding.FragmentProfileBinding
import org.three.minutes.home.viewmodel.HomeViewModel


class ProfileFragment : Fragment() {

    private lateinit var mActiviy : HomeActiviy
    private lateinit var mContext : Context
    private lateinit var mBinding : FragmentProfileBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActiviy = activity as HomeActiviy
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_profile,container,false)
        val mViewModel : HomeViewModel by activityViewModels()
        mBinding.apply {
            lifecycleOwner = this@ProfileFragment
            viewModel = mViewModel
        }

        //코루틴을 사용해서 시간 지나면 이미지 변경 + 데이터 바인딩
        CoroutineScope(Default).launch {
            launch{
                delay(5000)
            }.join()

            Log.d("checkIncrease", "${mViewModel.increDecre.value}")

            withContext(Main){
                mViewModel.increDecre.value = 2
            }

        }

        return mBinding.root
    }

}