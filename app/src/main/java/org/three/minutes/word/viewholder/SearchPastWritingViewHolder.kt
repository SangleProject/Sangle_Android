package org.three.minutes.word.viewholder

import android.annotation.SuppressLint
import android.content.Intent
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.three.minutes.R
import org.three.minutes.databinding.OtherWritingItemListBinding
import org.three.minutes.detail.ui.DetailActivity
import org.three.minutes.detail.ui.DetailMyActivity
import org.three.minutes.util.formatCount
import org.three.minutes.word.adapter.SearchWritingAdapter
import org.three.minutes.word.data.ResponsePastSearchData

class SearchPastWritingViewHolder (val binding : OtherWritingItemListBinding)
    : RecyclerView.ViewHolder(binding.root){

    @SuppressLint("SetTextI18n")
    fun onBind(data : ResponsePastSearchData, listener : SearchWritingAdapter.OnItemClickListener?){
        binding.itemContainer.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.item_scale_animation)
        binding.itemTitleTxt.text = data.topic
        binding.itemContents.text = data.postWrite
        binding.itemNickname.text = data.nickName
        binding.itemFavorite.text = data.likes.formatCount()
        binding.itemDateTxt.text = "${data.date} (${data.day}) ${data.time}"
        Glide.with(itemView).load(data.profileImg).into(binding.itemUserImg)
        binding.itemContents.setLines(10)
        binding.itemFavorite.isChecked = data.liked

        itemView.setOnClickListener {
            listener?.onPastSearchItemClick(itemView,data)

//            val intent : Intent = if (data.myNickName == data.nickName){
//                Intent(itemView.context, DetailMyActivity::class.java)
//            } else{
//                Intent(itemView.context, DetailActivity::class.java)
//            }
//
//            intent.putExtra("postIdx",data.postIdx)
//            itemView.context.startActivity(intent)
        }
    }
}