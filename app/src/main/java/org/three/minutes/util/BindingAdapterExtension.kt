package org.three.minutes.util


import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.three.minutes.R
import org.three.minutes.badge.adapter.BadgeListAdapter
import org.three.minutes.badge.data.ResponseBadgeData
import org.three.minutes.badge.ui.ClosedBadgePopup
import org.three.minutes.badge.ui.OpenedBadgePopup
import org.three.minutes.detail.data.ResponseMyWritingData
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.home.data.ResponseTodayTopicData
import org.three.minutes.mypage.adapter.MyScrapAdapter
import org.three.minutes.mypage.adapter.MyWritingAdapter
import org.three.minutes.word.adapter.SearchWritingAdapter
import org.three.minutes.word.adapter.TodayWordRcvAdapter
import org.three.minutes.word.data.ResponsePastSearchData
import org.three.minutes.word.data.ResponseSearchTopicData
import org.three.minutes.writing.data.BadgeData

@BindingAdapter("app:addTodayItem")
fun RecyclerView.setTodayWordData(data: MutableLiveData<List<ResponseTodayTopicData>>) {
    val adapter = TodayWordRcvAdapter(this.context)
    adapter.data = data.value!!
    this.adapter = adapter
    this.layoutManager =
        LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    this.addItemDecoration(WordRcvItemDeco(this.context, true, 4))
    adapter.notifyDataSetChanged()
}

@BindingAdapter("app:searchWritingItem")
fun RecyclerView.setSearchResult(data: MutableList<ResponseSearchTopicData>) {
    val adapter = SearchWritingAdapter(this.context, false)
    this.adapter = adapter
    this.layoutManager =
        LinearLayoutManager(this.context)

    adapter.resultData = data
    adapter.notifyDataSetChanged()
}

@BindingAdapter("app:pastDetailItem")
fun RecyclerView.setPastDetailItem(data: MutableList<ResponsePastSearchData>) {
    val adapter = SearchWritingAdapter(this.context)
    this.adapter = adapter
    this.layoutManager =
        LinearLayoutManager(this.context)

    adapter.pastData = data
    adapter.notifyDataSetChanged()
}

// 뱃지 리스트를 표현하는 바인딩 어댑터 Extension
@BindingAdapter("app:setBadgeList")
fun RecyclerView.setBadgeList(listData: MutableList<ResponseBadgeData>) {
    val rcvAdapter = BadgeListAdapter(this.context)

    rcvAdapter.setOnItemClickListener(object : BadgeListAdapter.OnItemClickListener {
        override fun onItemClick(v: View, data: ResponseBadgeData) {
            if (data.status) {
                OpenedBadgePopup(
                    v.context, BadgeData(
                        badgeName = data.badgeName,
                        badgeInfo = data.badgeInfo,
                        badgeImg = data.badgeImg
                    )
                ).show()
            } else {
                ClosedBadgePopup(
                    v.context, BadgeData(
                        badgeName = data.badgeName,
                        badgeInfo = data.badgeAddInfo,
                        badgeImg = data.badgeImg
                    )
                ).show()
            }
        }
    })
    this.apply {
        adapter = rcvAdapter
        addItemDecoration(
            RcvItemDeco(
                context = this.context,
                top = 16,
                bottom = 16,
                right = 0,
                left = 0,
                isGrid = true,
                spanCount = 3
            )
        )
    }
    rcvAdapter.data = listData
    rcvAdapter.notifyDataSetChanged()
}

@BindingAdapter("app:setGlide")
fun ImageView.setGlide(img: String?) {
    Glide.with(this.context)
        .load(img)
        .placeholder(R.drawable.profile1)
        .error(R.drawable.profile1)
        .into(this)
}

// My 서랍 내가 쓴 글 rcv
@BindingAdapter("app:setMyPost")
fun RecyclerView.setMyPost(data: MutableList<ResponseMyWritingData>) {
    val rcvAdapter = MyWritingAdapter(this.context)
    this.apply {
        adapter = rcvAdapter
        layoutManager = LinearLayoutManager(this.context)
    }
    rcvAdapter.data = data
    rcvAdapter.notifyDataSetChanged()
}

// My 서랍 담은 글 rcv
@BindingAdapter("app:setMyScrap")
fun RecyclerView.setMyScrap(data: MutableList<ResponseOtherWritingData>) {
    val rcvAdapter = MyScrapAdapter(this.context)
    this.apply {
        adapter = rcvAdapter
        layoutManager = LinearLayoutManager(this.context)
    }
    rcvAdapter.data = data
    rcvAdapter.notifyDataSetChanged()
}