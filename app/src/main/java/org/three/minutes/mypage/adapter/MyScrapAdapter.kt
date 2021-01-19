package org.three.minutes.mypage.adapter

import org.three.minutes.databinding.OtherWritingItemListBinding
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.mypage.viewholder.MyPostViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyScrapAdapter (private val context : Context) : RecyclerView.Adapter<MyPostViewHolder>(){

    var data = listOf<ResponseOtherWritingData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
        val binding = OtherWritingItemListBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyPostViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        holder.onBind(data[position])
    }

}