package org.three.minutes.badge.viewholder

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.badge.data.BadgeListData
import org.three.minutes.databinding.BadgeItemListBinding

class BadgeListViewHolder(val binding : BadgeItemListBinding):RecyclerView.ViewHolder(binding.root)
{
    fun onBind(data: BadgeListData){
        binding.badgeData = data
        when(data.isOpen){
            0 -> { // 아직 비활성화
                binding.badgeNameTxt.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.black_40)
                )
            }
            else -> { // 비활성화
                binding.badgeNameTxt.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.black_80)
                )
            }
        }
    }
}