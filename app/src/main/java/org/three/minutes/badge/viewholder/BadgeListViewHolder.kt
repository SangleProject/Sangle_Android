package org.three.minutes.badge.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.badge.adapter.BadgeListAdapter
import org.three.minutes.badge.data.ResponseBadgeData
import org.three.minutes.databinding.BadgeItemListBinding

class BadgeListViewHolder(val binding : BadgeItemListBinding) :RecyclerView.ViewHolder(binding.root)
{
    fun onBind(data: ResponseBadgeData,
    clickListener : BadgeListAdapter.OnItemClickListener){
        binding.badgeData = data
        if (data.status){
            binding.badgeNameTxt
                .setTextColor(ContextCompat.getColor(itemView.context, R.color.black_80))

        }
        else { // 비활성화
            binding.badgeNameTxt
                .setTextColor(ContextCompat.getColor(itemView.context, R.color.black_40))
        }

        itemView.setOnClickListener {
            clickListener.onItemClick(itemView, data)
        }
    }
}