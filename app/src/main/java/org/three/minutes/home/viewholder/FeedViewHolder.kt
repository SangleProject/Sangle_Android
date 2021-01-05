package org.three.minutes.home.viewholder

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.FeedListItemBinding
import org.three.minutes.detail.ui.DetailActivity
import org.three.minutes.detail.ui.DetailMyActivity
import org.three.minutes.home.data.FeedData

class FeedViewHolder (val binding : FeedListItemBinding) : RecyclerView.ViewHolder(binding.root){
    private lateinit var feedData: FeedData
    private lateinit var intent : Intent
    init{
        itemView.setOnClickListener {
            intent = if (feedData.isMy){
                Intent(binding.root.context, DetailMyActivity::class.java)
            } else{
                Intent(binding.root.context, DetailActivity::class.java)
            }
            intent.putExtra("feedData",feedData)
            binding.root.context.startActivity(intent)
        }
    }
    fun onBind(data : FeedData){
        feedData = data
        binding.data = feedData
    }
}