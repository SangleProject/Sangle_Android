package org.three.minutes.word.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.WordLockItemListBinding
import org.three.minutes.word.data.TodayWordData

class TodayWordViewHolder (val binding : WordLockItemListBinding) : RecyclerView.ViewHolder(binding.root){
    fun onBind(data : TodayWordData){
        binding.data = data
    }
}