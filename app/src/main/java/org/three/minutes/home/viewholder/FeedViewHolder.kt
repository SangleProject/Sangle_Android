package org.three.minutes.home.viewholder

import android.annotation.SuppressLint
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.FeedListItemBinding
import org.three.minutes.detail.ui.DetailActivity
import org.three.minutes.detail.ui.DetailMyActivity
import org.three.minutes.home.data.FeedData
import org.three.minutes.home.data.ResponseFameData
import org.three.minutes.util.formatCount

class FeedViewHolder (val binding : FeedListItemBinding) : RecyclerView.ViewHolder(binding.root){
    private lateinit var feedData: ResponseFameData
    private lateinit var intent : Intent
    init{
        itemView.setOnClickListener {
            intent = if (feedData.myNickName == feedData.nickName){
                Intent(binding.root.context, DetailMyActivity::class.java)
            } else{
                Intent(binding.root.context, DetailActivity::class.java)
            }
            intent.putExtra("feedData",feedData)
            binding.root.context.startActivity(intent)
        }
    }
    @SuppressLint("SetTextI18n")
    fun onBind(data : ResponseFameData){
        feedData = data
        binding.data = feedData
        binding.feedLikeCount.text = data.likes.formatCount()
        binding.feedDate.text = "${data.date} (${data.day}) ${data.time}"
    }
}