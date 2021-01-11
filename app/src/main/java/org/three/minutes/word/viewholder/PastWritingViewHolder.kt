package org.three.minutes.word.viewholder

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.databinding.PastWritingItemListBinding
import org.three.minutes.word.data.ResponseLastTopicData

class PastWritingViewHolder(val binding: PastWritingItemListBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun onBind(data : ResponseLastTopicData){
        binding.pastContainer.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.item_scale_animation)
        binding.data = data
    }
}