package org.three.minutes.mypage.viewholder

import androidx.recyclerview.widget.RecyclerView
import min.dev.singleclick.mingSingleClickListener
import org.three.minutes.databinding.ItemReportTopicBinding
import org.three.minutes.detail.data.ResponseMyWritingData
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.word.ui.DialogReportedTopic

class ReportPostViewHolder(val binding: ItemReportTopicBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val reportDialog by lazy { DialogReportedTopic(binding.root.context) }

    fun onBindMyWriting(data: ResponseMyWritingData) {
        itemView.mingSingleClickListener {
            reportDialog.showMyReportedTopic(data)
        }
    }

    fun onBindOtherWriting(data: ResponseOtherWritingData) {
        itemView.mingSingleClickListener {
            reportDialog.showOtherReportedTopic(data)
        }
    }
}