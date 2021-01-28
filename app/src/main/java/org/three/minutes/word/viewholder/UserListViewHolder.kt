package org.three.minutes.word.viewholder

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.databinding.UserListItemBinding
import org.three.minutes.word.data.ResponseSearchTopicData

class UserListViewHolder(val binding : UserListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data : ResponseSearchTopicData){
        binding.userListContainer.animation = AnimationUtils
            .loadAnimation(itemView.context, R.anim.item_scale_animation)
        binding.data = data
    }
}