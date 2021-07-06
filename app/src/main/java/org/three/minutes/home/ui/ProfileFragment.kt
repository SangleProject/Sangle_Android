package org.three.minutes.home.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.*
import org.three.minutes.R
import org.three.minutes.databinding.FragmentProfileBinding
import org.three.minutes.home.viewmodel.HomeViewModel
import org.three.minutes.singleton.PopUpObject
import org.three.minutes.util.showToast
import org.three.minutes.writing.ui.WritingReadyActivity
import kotlin.coroutines.CoroutineContext


class ProfileFragment : Fragment(),CoroutineScope {

    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var mActivity : HomeActivity
    private lateinit var mContext : Context
    private lateinit var mBinding : FragmentProfileBinding
    private val mViewModel : HomeViewModel by activityViewModels()
    private val progress by lazy{ PopUpObject.setLoading(activity as HomeActivity)}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as HomeActivity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        job = Job()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_profile,container,false)
        mBinding.apply {
            lifecycleOwner = this@ProfileFragment
            viewModel = mViewModel
            fragment = this@ProfileFragment
        }

        mViewModel.topic.observe(viewLifecycleOwner, {
            // 토픽 받기 통신 성공 후 글감을 받아 왔을 경우
            if (it.isNotEmpty()){
                launch {
                    delay(2000)
                    progress.dismiss()
                    val intent = Intent(mContext, WritingReadyActivity::class.java)
                    intent.putExtra("topic",it)
                    startActivity(intent)
                }
            }
        })

        return mBinding.root
    }

    fun goToWriting(){
        if (mViewModel.remaining.value == 0){
            mContext.showToast("이런! 오늘 글감을 모두 썼어요!")
        }
        else{
            progress.show()
            mViewModel.callTopic()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}