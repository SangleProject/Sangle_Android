package org.three.minutes.home.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import org.three.minutes.writing.ui.WritingReadyActivity
import kotlin.coroutines.CoroutineContext


class ProfileFragment : Fragment(),CoroutineScope {

    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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
        job = Job()
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
            fragment = this@ProfileFragment
        }

        return mBinding.root
    }

    fun goToWriting(){
        launch {
            val progress = PopUpObject.setLoading(activity as HomeActiviy)
            progress.show()

            delay(2000)
            progress.dismiss()

            val intent = Intent(mContext, WritingReadyActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}