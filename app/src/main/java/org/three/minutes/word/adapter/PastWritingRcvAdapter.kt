package org.three.minutes.word.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.PastWritingItemListBinding
import org.three.minutes.word.data.PastWritingData
import org.three.minutes.word.viewholder.PastWritingViewHolder

class PastWritingRcvAdapter (val context : Context) : RecyclerView.Adapter<PastWritingViewHolder>(){

    var data = mutableListOf<PastWritingData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastWritingViewHolder {

        val binding = PastWritingItemListBinding.inflate(LayoutInflater.from(context),
        parent,false)

        return PastWritingViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PastWritingViewHolder, position: Int) {
        holder.onBind(data[position])
    }
}