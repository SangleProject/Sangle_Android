package org.three.minutes.word.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.WordLockItemListBinding
import org.three.minutes.home.data.ResponseTodayTopicData
import org.three.minutes.word.viewholder.TodayWordViewHolder

class TodayWordRcvAdapter (private val context : Context) : RecyclerView.Adapter<TodayWordViewHolder>(){

    var data = listOf<ResponseTodayTopicData>()

    private var listener: TodayWordListener? = null

    interface TodayWordListener {
        fun onItemClick(v: View, data: ResponseTodayTopicData)
    }

    fun setTodayWordListener(l: TodayWordListener) {
        listener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayWordViewHolder {
        val binding = WordLockItemListBinding.inflate(LayoutInflater.from(context),
            parent,false)
        return TodayWordViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TodayWordViewHolder, position: Int) {
        holder.onBind(data[position], listener)
    }
}