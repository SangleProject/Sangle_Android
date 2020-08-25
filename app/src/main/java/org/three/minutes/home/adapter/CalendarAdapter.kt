package org.three.minutes.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.R
import org.three.minutes.databinding.CalendarDayItemBinding
import org.three.minutes.home.data.CalendarData
import org.three.minutes.home.viewholder.CalendarEmptyViewHolder
import org.three.minutes.home.viewholder.CalendarViewHolder

class CalendarAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = arrayListOf<CalendarData>()

    override fun getItemViewType(position: Int): Int {
        return when (data[position].empty) {
            true -> 0

            false -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val binding =
                CalendarDayItemBinding.inflate(LayoutInflater.from(context), parent, false)
            return CalendarViewHolder(binding)
        } else {
            val view =
                LayoutInflater.from(context).inflate(R.layout.calendar_empty_item, parent, false)
            return CalendarEmptyViewHolder(view)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CalendarViewHolder -> {
                holder.onBind(data[position])
            }
            else ->{
            }
        }
    }
}