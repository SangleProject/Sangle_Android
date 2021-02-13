package org.three.minutes.word.ui

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.three.minutes.CloseTopicPopUp
import org.three.minutes.CloseTopicPopUpListener
import org.three.minutes.R
import org.three.minutes.databinding.FragmentWordBinding
import org.three.minutes.home.ui.HomeActivity
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.singleton.PopUpObject
import org.three.minutes.util.WordRcvItemDeco
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.showToast
import org.three.minutes.word.adapter.PastWritingRcvAdapter
import org.three.minutes.word.data.RequestWrittenData
import org.three.minutes.word.data.ResponseLastTopicData
import org.three.minutes.word.viewmodel.WordViewModel
import org.three.minutes.writing.ui.WritingReadyActivity


class WordFragment : Fragment(),CloseTopicPopUpListener {
    private lateinit var rcvAdapter: PastWritingRcvAdapter
    private lateinit var mBinding: FragmentWordBinding
    private lateinit var wordActivity: WordActivity
    private val writtenPopUP : CloseTopicPopUp by lazy {
        CloseTopicPopUp(mBinding.root.context,this)
    }

    private val mViewModel: WordViewModel by activityViewModels()
    private val progress by lazy { PopUpObject.setLoading(activity as WordActivity) }

    override fun clickGoToWritingButton() {
        val intent = Intent(mBinding.root.context,HomeActivity::class.java)
        startActivity(intent)
        activity?.finishAndRemoveTask()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        wordActivity = activity as WordActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_word, container, false)
        // 상단 하단 글감 가져오기 api

        mBinding.viewModel = mViewModel
        mBinding.activity = activity as WordActivity
        mBinding.lifecycleOwner = viewLifecycleOwner

        mViewModel.lastTopicOk.observe(viewLifecycleOwner,{
            if (it){
                rcvAdapter = PastWritingRcvAdapter(mBinding.root.context)
                rcvAdapter.data = mViewModel.lastTopicList.value!!
                rcvAdapter.setOnItemClickListener(object : PastWritingRcvAdapter.OnItemClickListener {
                    override fun onItemClick(v: View, data: ResponseLastTopicData) {
                        mViewModel.callPastDetailPopular(data.topic)
                        mViewModel.filter.value = "최신순"
                        SangleServiceImpl.service.postWritten(
                            token = mViewModel.token,
                            body = RequestWrittenData(topic = data.topic)
                        ).customEnqueue(
                            onSuccess = {
                                if (it.written){
                                    wordActivity.replaceDetailFragment()
                                }
                                else{
                                    writtenPopUP.show()
                                }
                            }
                        )

                    }
                })
                mBinding.pastWritingRcv.apply {
                    adapter = rcvAdapter
                    layoutManager = LinearLayoutManager(mBinding.root.context)
                    addItemDecoration(WordRcvItemDeco(this.context, false, 3))
                }
                rcvAdapter.notifyDataSetChanged()
            }
        })



        // 카테고리 변경 시 글씨체 변경
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 글 쓰러 가기 버튼 클릭 시 글 쓰러 가기
        mBinding.gotoWritingBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                progress.show()
                var topic = ""
                for (i in mViewModel.todayTopicList.value!!) {
                    if (!i.used) {
                        topic = i.topic
                        break
                    }
                }
                delay(2000)
                if (topic.isBlank()) {
                    view.context.showToast("이런! 오늘 글감을 모두 썼어요!")
                } else {
                    val intent = Intent(view.context, WritingReadyActivity::class.java)
                    intent.putExtra("topic", topic)
                    startActivity(intent)
                }
                progress.dismiss()
            }
        }

        setFilterObserving()
        setFilterClickListener()
    }

    private fun setFilterClickListener() {
        mBinding.pastWritingFilter.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.past_writing_all -> mViewModel.allCheck.value = true
                R.id.past_writing_done -> mViewModel.doneCheck.value = true
                R.id.past_writing_not -> mViewModel.notCheck.value = true
            }
        }
    }

    private fun setFilterObserving() {

        mViewModel.allCheck.observe(viewLifecycleOwner, { check ->
            if (check) {
                mBinding.pastWritingAll.typeface = Typeface.DEFAULT_BOLD
                mViewModel.doneCheck.value = false
                mViewModel.notCheck.value = false
            } else {
                mBinding.pastWritingAll.typeface = Typeface.DEFAULT
            }
        })

        mViewModel.doneCheck.observe(viewLifecycleOwner, { check ->
            if (check) {
                mBinding.pastWritingDone.typeface = Typeface.DEFAULT_BOLD
                mViewModel.allCheck.value = false
                mViewModel.notCheck.value = false
            } else {
                mBinding.pastWritingDone.typeface = Typeface.DEFAULT
            }
        })

        mViewModel.notCheck.observe(viewLifecycleOwner, { check ->
            if (check) {
                mBinding.pastWritingNot.typeface = Typeface.DEFAULT_BOLD
                mViewModel.allCheck.value = false
                mViewModel.doneCheck.value = false
            } else {
                mBinding.pastWritingNot.typeface = Typeface.DEFAULT
            }
        })
    }
}