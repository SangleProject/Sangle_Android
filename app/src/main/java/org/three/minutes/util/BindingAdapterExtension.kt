package org.three.minutes.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.word.adapter.PastWritingRcvAdapter
import org.three.minutes.word.adapter.TodayWordRcvAdapter
import org.three.minutes.word.data.PastWritingData
import org.three.minutes.word.data.TodayWordData

@BindingAdapter("app:addTodayItem")
fun RecyclerView.setTodayWordData(data : MutableList<TodayWordData>){
    val adapter = TodayWordRcvAdapter(this.context)
    adapter.data = data
    this.adapter = adapter
    this.layoutManager =
        LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
    this.addItemDecoration(WordRcvItemDeco(this.context,true,4))
    adapter.notifyDataSetChanged()
}

@BindingAdapter("app:addPastItem")
fun RecyclerView.setPastWritingData(data : MutableList<PastWritingData>){
    val adapter = PastWritingRcvAdapter(this.context)
    adapter.data = data
    this.adapter = adapter
    this.layoutManager =
        LinearLayoutManager(this.context)
    this.addItemDecoration(WordRcvItemDeco(this.context,false,6))
    adapter.notifyDataSetChanged()
}