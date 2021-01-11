package org.three.minutes.word.ui

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import org.three.minutes.R
import org.three.minutes.databinding.FragmentWordBinding
import org.three.minutes.word.viewmodel.WordViewModel


class WordFragment : Fragment() {
    private lateinit var mBinding : FragmentWordBinding
    private val mViewModel : WordViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_word, container,false)
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        // 카테고리 변경 시 글씨체 변경
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilterObserving()
        setFilterClickListener()
    }

    private fun setFilterClickListener() {
        mBinding.pastWritingFilter.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.past_writing_all -> mViewModel.allCheck.value = true
                R.id.past_writing_done -> mViewModel.doneCheck.value = true
                R.id.past_writing_not -> mViewModel.notCheck.value = true
            }
        }
    }

    private fun setFilterObserving() {

        mViewModel.allCheck.observe(viewLifecycleOwner, { check ->
            if (check){
                mBinding.pastWritingAll.typeface = Typeface.DEFAULT_BOLD
                mViewModel.doneCheck.value=false
                mViewModel.notCheck.value=false
            }
            else{
                mBinding.pastWritingAll.typeface = Typeface.DEFAULT
            }
        })

        mViewModel.doneCheck.observe(viewLifecycleOwner,{check ->
            if (check){
                mBinding.pastWritingDone.typeface = Typeface.DEFAULT_BOLD
                mViewModel.allCheck.value=false
                mViewModel.notCheck.value=false
            }
            else{
                mBinding.pastWritingDone.typeface = Typeface.DEFAULT
            }
        })

        mViewModel.notCheck.observe(viewLifecycleOwner,{check ->
            if (check){
                mBinding.pastWritingNot.typeface = Typeface.DEFAULT_BOLD
                mViewModel.allCheck.value=false
                mViewModel.doneCheck.value=false
            }
            else{
                mBinding.pastWritingNot.typeface = Typeface.DEFAULT
            }
        })
    }
}