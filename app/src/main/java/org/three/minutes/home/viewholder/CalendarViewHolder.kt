package org.three.minutes.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.CalendarDayItemBinding
import org.three.minutes.home.data.CalendarData

class CalendarViewHolder (val binding : CalendarDayItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun onBind(data : CalendarData){
        binding.calendarData = data
    }
}