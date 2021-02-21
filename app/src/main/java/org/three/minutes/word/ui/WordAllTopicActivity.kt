package org.three.minutes.word.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.three.minutes.R
import org.three.minutes.databinding.ActivityWordAllTopicBinding
import org.three.minutes.word.adapter.PastWritingRcvAdapter
import org.three.minutes.word.viewmodel.WordAllTopicViewModel

class WordAllTopicActivity : AppCompatActivity() {

    private val mBinding : ActivityWordAllTopicBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_word_all_topic)
    }

    private val mAdapter : PastWritingRcvAdapter by lazy {
        PastWritingRcvAdapter(mBinding.root.context)
    }

    private val mViewModel : WordAllTopicViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@WordAllTopicActivity
        }

        setObserve()
        setClickEvent()
        mViewModel.getAllTopic()

    }

    private fun setClickEvent() {
        mBinding.wordToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setObserve() {
        mViewModel.getToken.observe(this,{
            mViewModel.token = it
        })

        mViewModel.lastTopicList.observe(this,{
            if (it.isNotEmpty()){
                mAdapter.data = it
                mBinding.allTopicList.adapter = mAdapter
                mBinding.allTopicList.layoutManager = LinearLayoutManager(this)
                mAdapter.notifyDataSetChanged()
            }
        })
    }
}