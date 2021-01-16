package org.three.minutes.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.three.minutes.R
import org.three.minutes.badge.adapter.BadgeListAdapter
import org.three.minutes.badge.data.BadgeListData
import org.three.minutes.home.data.ResponseTodayTopicData
import org.three.minutes.mypage.adapter.MyWritingAdapter
import org.three.minutes.mypage.data.MyWritingData
import org.three.minutes.word.adapter.PastWritingRcvAdapter
import org.three.minutes.word.adapter.SearchWritingAdapter
import org.three.minutes.word.adapter.TodayWordRcvAdapter
import org.three.minutes.word.data.ResponseLastTopicData
import org.three.minutes.word.data.ResponseSearchData
import org.three.minutes.word.data.SearchWritingData
import org.three.minutes.word.ui.PostDetailFragment
import org.three.minutes.word.ui.SearchEmptyFragment
import org.three.minutes.word.ui.WordActivity

@BindingAdapter("app:addTodayItem")
fun RecyclerView.setTodayWordData(data : MutableLiveData<List<ResponseTodayTopicData>>){
    val adapter = TodayWordRcvAdapter(this.context)
    adapter.data = data.value!!
    this.adapter = adapter
    this.layoutManager =
        LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
    this.addItemDecoration(WordRcvItemDeco(this.context,true,4))
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
fun RecyclerView.setSearchResult(data : MutableList<ResponseSearchData>){
    val adapter = SearchWritingAdapter(this.context)
    this.adapter = adapter
    this.layoutManager =
        LinearLayoutManager(this.context)

    adapter.data = data
    adapter.notifyDataSetChanged()
}

@BindingAdapter("app:pastDetailItem")
fun RecyclerView.setPastDetailItem(data : MutableList<ResponseSearchData>){
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
    Log.d("Show BindingAdapter","call")
//    val openedBadgePopup = OpenedBadgePopup(this.context)
//    val closedBadgePopup = ClosedBadgePopup(this.context)
    val rcvAdapter = BadgeListAdapter(this.context)

    rcvAdapter.setOnItemClickListener(object : BadgeListAdapter.OnItemClickListener{
        override fun onItemClick(v: View, data: BadgeListData) {
            when(data.isOpen){
                // 뱃지가 닫힌 경우
                0 -> {
//                    closedBadgePopup.show()

                }
                // 뱃지 오픈
                else->{
//                    openedBadgePopup.show()
                }
            }

        }
    })
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

@BindingAdapter("app:setGlide")
fun ImageView.setGlide(img : String?){
    Glide.with(this.context)
        .load(img)
        .placeholder(R.drawable.profile1)
        .error(R.drawable.profile1)
        .into(this)
}