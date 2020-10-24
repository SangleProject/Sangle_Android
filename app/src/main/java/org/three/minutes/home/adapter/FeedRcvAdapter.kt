package org.three.minutes.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.FeedListItemBinding
import org.three.minutes.home.data.FeedData
import org.three.minutes.home.viewholder.FeedViewHolder

class FeedRcvAdapter (val context : Context) : RecyclerView.Adapter<FeedViewHolder>(){
    var data = listOf<FeedData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = FeedListItemBinding.inflate(LayoutInflater.from(context), parent,false)

        return FeedViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.onBind(data[position])
    }
}