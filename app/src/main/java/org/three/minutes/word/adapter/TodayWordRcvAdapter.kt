package org.three.minutes.word.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.WordLockItemListBinding
import org.three.minutes.word.data.TodayWordData
import org.three.minutes.word.viewholder.TodayWordViewHolder

class TodayWordRcvAdapter (private val context : Context) : RecyclerView.Adapter<TodayWordViewHolder>(){

    var data = mutableListOf<TodayWordData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayWordViewHolder {
        val binding = WordLockItemListBinding.inflate(LayoutInflater.from(context),
            parent,false)
        return TodayWordViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TodayWordViewHolder, position: Int) {
        holder.onBind(data[position])
    }
}