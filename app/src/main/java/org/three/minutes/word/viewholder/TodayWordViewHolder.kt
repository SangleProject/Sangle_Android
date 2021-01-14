package org.three.minutes.word.viewholder

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.databinding.WordLockItemListBinding
import org.three.minutes.home.data.ResponseTodayTopicData

class TodayWordViewHolder (val binding : WordLockItemListBinding) : RecyclerView.ViewHolder(binding.root){

    fun onBind(data : ResponseTodayTopicData){

            binding.todayContainer.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.item_scale_animation)
            binding.data = data

    }
}