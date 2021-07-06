package org.three.minutes.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.three.minutes.R
import org.three.minutes.databinding.CalendarDayItemBinding
import org.three.minutes.home.data.CalendarData

class CalendarViewHolder (val binding : CalendarDayItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun onBind(data : CalendarData){

        binding.calendarData = data
        when(data.count){
            1->{
                Glide.with(itemView).load(R.drawable.circle_light_mint).into(binding.calendarCircle)
            }
            2 ->{
                Glide.with(itemView).load(R.drawable.circle_sky_blue).into(binding.calendarCircle)
            }
            3 ->{
                Glide.with(itemView).load(R.drawable.circle_deep_sky).into(binding.calendarCircle)

            }
        }
    }
}