package org.three.minutes.badge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.badge.data.BadgeListData
import org.three.minutes.badge.viewholder.BadgeListViewHolder
import org.three.minutes.databinding.BadgeItemListBinding

class BadgeListAdapter(val context : Context) : RecyclerView.Adapter<BadgeListViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(v : View, data : BadgeListData)
    }

    var data = mutableListOf<BadgeListData>()
    private lateinit var listener : OnItemClickListener

    fun setOnItemClickListener(listener : OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeListViewHolder {
        val binding = BadgeItemListBinding.inflate(LayoutInflater.from(context),parent,false)
        return BadgeListViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BadgeListViewHolder, position: Int) {
        holder.onBind(data[position],listener)
    }
}