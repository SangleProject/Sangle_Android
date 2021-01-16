package org.three.minutes.word.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.OtherWritingItemListBinding
import org.three.minutes.word.data.ResponseSearchData
import org.three.minutes.word.viewholder.SearchWritingViewHolder

class SearchWritingAdapter (val context : Context) : RecyclerView.Adapter<SearchWritingViewHolder>(){

    var data = listOf<ResponseSearchData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchWritingViewHolder {
        val binding = OtherWritingItemListBinding.inflate(LayoutInflater.from(context),
        parent,false)

        return SearchWritingViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SearchWritingViewHolder, position: Int) {
        holder.onBind(data[position])
    }
}