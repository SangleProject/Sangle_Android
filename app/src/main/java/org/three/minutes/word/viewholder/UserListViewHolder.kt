package org.three.minutes.word.viewholder

import android.content.Intent
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.databinding.UserListItemBinding
import org.three.minutes.profile.ui.OtherProfileActivity
import org.three.minutes.word.data.ResponseSearchTopicData
import org.three.minutes.word.data.ResponseUserListData

class UserListViewHolder(val binding : UserListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data : ResponseUserListData){
        binding.userListContainer.animation = AnimationUtils
            .loadAnimation(itemView.context, R.anim.item_scale_animation)
        binding.data = data

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, OtherProfileActivity::class.java)
            intent.putExtra("nickName",data.nickName)
            itemView.context.startActivity(intent)
        }
    }
}