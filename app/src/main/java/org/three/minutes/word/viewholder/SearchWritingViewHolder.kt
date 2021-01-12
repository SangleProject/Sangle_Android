package org.three.minutes.word.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.OtherWritingItemListBinding
import org.three.minutes.util.formatCount
import org.three.minutes.word.data.ResponseSearchData

class SearchWritingViewHolder (val binding : OtherWritingItemListBinding)
    : RecyclerView.ViewHolder(binding.root){

    @SuppressLint("SetTextI18n")
    fun onBind(data : ResponseSearchData){
        binding.data = data
        binding.itemDateTxt.text = "${data.date} (${data.day}) ${data.time}"
        binding.itemFavorite.text = data.likes.formatCount()
    }
}