package org.three.minutes.profile.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.databinding.MyWritingItemListBinding
import org.three.minutes.detail.data.ResponseOtherWritingData
import org.three.minutes.detail.ui.DetailActivity
import org.three.minutes.util.formatCount

class OtherProfileRcvAdapter
    : RecyclerView.Adapter<OtherProfileRcvAdapter.OtherProfileViewHolder>() {

    var data = listOf<ResponseOtherWritingData>()

    interface OnItemClickListener{
        fun onItemClick(v : View, data : ResponseOtherWritingData)
    }

    private lateinit var listener : OnItemClickListener

    fun setOnClickListener(listener : OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherProfileViewHolder {
        val binding = MyWritingItemListBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return OtherProfileViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: OtherProfileViewHolder, position: Int) {
        holder.onBind(data[position])
    }


    inner class OtherProfileViewHolder(val binding : MyWritingItemListBinding)
        : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun onBind(data : ResponseOtherWritingData){
            binding.container.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.item_scale_animation)

            binding.itemTitleTxt.text = data.topic
            binding.itemDateTxt.text = "${data.date} (${data.day}) ${data.time}"
            binding.itemContents.text = data.postWrite
            binding.itemFavorite.text = data.likes.formatCount()
            binding.itemFavorite.isChecked = data.liked

            itemView.setOnClickListener {
                listener.onItemClick(itemView,data)
            }
        }
    }
}