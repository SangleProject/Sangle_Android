package org.three.minutes.word.viewholder

import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.databinding.WordLockItemListBinding
import org.three.minutes.home.data.ResponseTodayTopicData
import org.three.minutes.word.adapter.TodayWordRcvAdapter

class TodayWordViewHolder(val binding: WordLockItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: ResponseTodayTopicData, listener: TodayWordRcvAdapter.TodayWordListener?) {

        binding.todayContainer.animation =
            AnimationUtils.loadAnimation(itemView.context, R.anim.item_scale_animation)
        binding.data = data

        if (data.used) {
            itemView.setOnClickListener {
                listener?.onItemClick(itemView, data)
            }
        }

    }
}