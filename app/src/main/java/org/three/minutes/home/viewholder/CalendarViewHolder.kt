package org.three.minutes.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.databinding.CalendarDayItemBinding
import org.three.minutes.home.data.CalendarData

class CalendarViewHolder (val binding : CalendarDayItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun onBind(data : CalendarData){

        binding.calendarData = data
        when(data.count){
            0 -> {
                binding.calendarDay.setBackgroundResource(R.drawable.circle_gray)
            }
            1->{
                binding.calendarDay.setBackgroundResource(R.drawable.circle_light_mint)
            }
            2 ->{
                binding.calendarDay.setBackgroundResource(R.drawable.circle_sky_blue)
            }
            3 ->{
                binding.calendarDay.setBackgroundResource(R.drawable.circle_deep_sky)
            }
        }
    }
}