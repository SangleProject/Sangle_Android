package org.three.minutes.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.word.adapter.TodayWordRcvAdapter
import org.three.minutes.word.data.TodayWordData

@BindingAdapter("app:itemlist")
fun RecyclerView.setData(data : MutableList<TodayWordData>){
    val adapter = TodayWordRcvAdapter(this.context)
    adapter.data = data
    this.adapter = adapter
    this.layoutManager =
        LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
    this.addItemDecoration(TodayRcvItemDeco(this.context,true,4))
    adapter.notifyDataSetChanged()
}