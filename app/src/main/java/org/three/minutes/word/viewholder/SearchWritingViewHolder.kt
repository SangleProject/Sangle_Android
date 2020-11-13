package org.three.minutes.word.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.OtherWritingItemListBinding
import org.three.minutes.word.data.SearchWritingData

class SearchWritingViewHolder (val binding : OtherWritingItemListBinding)
    : RecyclerView.ViewHolder(binding.root){

    fun onBind(data : SearchWritingData){
        binding.data = data
    }
}