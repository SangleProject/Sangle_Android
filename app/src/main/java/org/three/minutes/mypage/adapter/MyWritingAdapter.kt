package org.three.minutes.mypage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.MyWritingItemListBinding
import org.three.minutes.mypage.data.MyWritingData
import org.three.minutes.mypage.viewholder.MyWritingViewHolder

class MyWritingAdapter (private val context : Context) : RecyclerView.Adapter<MyWritingViewHolder>(){

    var data = mutableListOf<MyWritingData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWritingViewHolder {
        val binding = MyWritingItemListBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyWritingViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyWritingViewHolder, position: Int) {
        holder.onBind(data[position])
    }

}