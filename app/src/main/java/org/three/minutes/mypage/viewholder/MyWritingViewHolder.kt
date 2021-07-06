package org.three.minutes.mypage.viewholder

import android.annotation.SuppressLint
import android.content.Intent
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.databinding.MyWritingItemListBinding
import org.three.minutes.detail.data.ResponseMyWritingData
import org.three.minutes.detail.ui.DetailMyActivity
import org.three.minutes.util.formatCount

class MyWritingViewHolder (val binding : MyWritingItemListBinding) : RecyclerView.ViewHolder(binding.root){

    @SuppressLint("SetTextI18n")
    fun onBind(data : ResponseMyWritingData){
        binding.container.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.item_scale_animation)

        binding.itemTitleTxt.text = data.topic
        binding.itemDateTxt.text = "${data.date} (${data.day}) ${data.time}"
        binding.itemContents.text = data.postWrite
        binding.itemFavorite.text = data.likes.formatCount()
        binding.itemFavorite.isChecked = data.liked

        itemView.setOnClickListener {
            val intent = Intent(itemView.context,DetailMyActivity::class.java)
            intent.putExtra("postIdx",data.postIdx)
            itemView.context.startActivity(intent)
        }
    }
}