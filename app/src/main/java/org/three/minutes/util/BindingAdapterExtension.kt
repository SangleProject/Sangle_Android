package org.three.minutes.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.badge.adapter.BadgeListAdapter
import org.three.minutes.badge.data.BadgeListData
import org.three.minutes.mypage.adapter.MyWritingAdapter
import org.three.minutes.mypage.data.MyWritingData
import org.three.minutes.word.adapter.PastWritingRcvAdapter
import org.three.minutes.word.adapter.SearchWritingAdapter
import org.three.minutes.word.adapter.TodayWordRcvAdapter
import org.three.minutes.word.data.PastWritingData
import org.three.minutes.word.data.SearchWritingData
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

@BindingAdapter("app:myWritingItem")
fun RecyclerView.setMyWritingData(data : MutableList<MyWritingData>){
    val adapter = MyWritingAdapter(this.context)
    this.adapter = adapter
    this.layoutManager =
        LinearLayoutManager(this.context)

    adapter.data = data
    adapter.notifyDataSetChanged()
}

@BindingAdapter("app:searchWritingItem")
fun RecyclerView.setSearchResult(data : List<SearchWritingData>){
    val adapter = SearchWritingAdapter(this.context)
    this.adapter = adapter
    this.layoutManager =
        LinearLayoutManager(this.context)

    adapter.data = data
    adapter.notifyDataSetChanged()
}
// 뱃지 리스트를 표현하는 바인딩 어댑터 Extension
@BindingAdapter("app:setBadgeList")
fun RecyclerView.setBadgeList(listData : MutableList<BadgeListData>){
    val rcvAdapter = BadgeListAdapter(this.context)
    this.apply {
        adapter = rcvAdapter
        addItemDecoration(RcvItemDeco(
            context =this.context,
            top = 16,
            bottom = 16,
            right = 0,
            left = 0,
            isGrid = true,
            spanCount = 3
        ))
    }
    rcvAdapter.data = listData
    rcvAdapter.notifyDataSetChanged()
}