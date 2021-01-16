package org.three.minutes.word.viewholder

import android.annotation.SuppressLint
import android.content.Intent
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.databinding.OtherWritingItemListBinding
import org.three.minutes.detail.ui.DetailActivity
import org.three.minutes.detail.ui.DetailMyActivity
import org.three.minutes.util.formatCount
import org.three.minutes.word.data.ResponseSearchData

class SearchWritingViewHolder (val binding : OtherWritingItemListBinding)
    : RecyclerView.ViewHolder(binding.root){

    @SuppressLint("SetTextI18n")
    fun onBind(data : ResponseSearchData){
        binding.itemContainer.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.item_scale_animation)
        binding.data = data
        binding.itemDateTxt.text = "${data.date} (${data.day}) ${data.time}"
        binding.itemFavorite.text = data.likes.formatCount()
        binding.itemContents.setLines(10)

        itemView.setOnClickListener {
            val intent : Intent = if (data.myNickName == data.nickName){
                Intent(itemView.context, DetailMyActivity::class.java)
            } else{
                Intent(itemView.context, DetailActivity::class.java)
            }

            intent.putExtra("postIdx",data.postIdx)
            itemView.context.startActivity(intent)
        }
    }
}