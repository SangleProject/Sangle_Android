package org.three.minutes.word.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.PastWritingItemListBinding
import org.three.minutes.word.data.ResponseLastTopicData

class PastWritingViewHolder(val binding: PastWritingItemListBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun onBind(data : ResponseLastTopicData){
        binding.data = data
    }
}