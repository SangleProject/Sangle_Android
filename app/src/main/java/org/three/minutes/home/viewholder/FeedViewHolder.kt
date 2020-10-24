package org.three.minutes.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.FeedListItemBinding
import org.three.minutes.home.data.FeedData

class FeedViewHolder (val binding : FeedListItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun onBind(data : FeedData){
        binding.data = data
    }
}