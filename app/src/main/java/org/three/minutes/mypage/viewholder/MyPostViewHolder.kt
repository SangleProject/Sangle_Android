package org.three.minutes.mypage.viewholder

import android.annotation.SuppressLint
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.three.minutes.databinding.OtherWritingItemListBinding
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.detail.ui.DetailActivity
import org.three.minutes.util.formatCount

class MyPostViewHolder(val binding : OtherWritingItemListBinding) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun onBind(data : ResponseOtherWritingData){
        binding.itemTitleTxt.text = data.topic
        binding.itemDateTxt.text = "${data.date} (${data.day} ${data.time})"
        binding.itemContents.text = data.postWrite
        Glide.with(itemView).load(data.profileImg).into(binding.itemUserImg)
        binding.itemNickname.text = data.nickName
        binding.itemFavorite.text = data.likes.formatCount()
        binding.itemFavorite.isChecked = data.liked

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailActivity::class.java)
            intent.putExtra("postIdx",data.postIdx)
            itemView.context.startActivity(intent)
        }
    }
}