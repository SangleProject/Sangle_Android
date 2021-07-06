package org.three.minutes.mypage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.ItemReportTopicBinding
import org.three.minutes.databinding.MyWritingItemListBinding
import org.three.minutes.detail.data.ResponseMyWritingData
import org.three.minutes.mypage.viewholder.MyWritingViewHolder
import org.three.minutes.mypage.viewholder.ReportPostViewHolder

class MyWritingAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<ResponseMyWritingData>()

    override fun getItemViewType(position: Int): Int {
        return if (data[position].isBan.isNotBlank())
            0
        else
            1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val binding = ItemReportTopicBinding.inflate(LayoutInflater.from(context), parent, false)
                ReportPostViewHolder(binding)
            }
            else -> {
                val binding =
                    MyWritingItemListBinding.inflate(LayoutInflater.from(context), parent, false)
                MyWritingViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReportPostViewHolder -> {
                holder.onBindMyWriting(data[position])
            }
            is MyWritingViewHolder -> {
                holder.onBind(data[position])
            }
        }
    }

}