package org.three.minutes.mypage.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.MyWritingItemListBinding
import org.three.minutes.mypage.data.MyWritingData

class MyWritingViewHolder (val binding : MyWritingItemListBinding) : RecyclerView.ViewHolder(binding.root){
    fun onBind(data : MyWritingData){
        binding.data = data
    }
}