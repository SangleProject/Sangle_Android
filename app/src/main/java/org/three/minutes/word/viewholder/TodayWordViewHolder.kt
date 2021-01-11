package org.three.minutes.word.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.WordLockItemListBinding
import org.three.minutes.home.data.ResponseTodayTopicData

class TodayWordViewHolder (val binding : WordLockItemListBinding) : RecyclerView.ViewHolder(binding.root){
    fun onBind(data : ResponseTodayTopicData){
        binding.data = data
    }
}