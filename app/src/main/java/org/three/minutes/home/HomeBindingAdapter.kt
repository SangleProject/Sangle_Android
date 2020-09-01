package org.three.minutes.home

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.three.minutes.R
import org.three.minutes.home.adapter.CalendarAdapter
import org.three.minutes.home.data.CalendarData

object HomeBindingAdapter {

    @BindingAdapter("writingImg")
    @JvmStatic
    fun loadingWritingImg(view : ImageView, writingCount : Int){
        val glideManager = Glide.with(view).asGif()
        when (writingCount){
            0 -> {
                glideManager.load(R.raw.sample_none).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(view)

            }
        }
    }

    @BindingAdapter("progressImg")
    @JvmStatic
    fun loadProgressImg(view : ImageView, checkCount : Int){
        when(checkCount){
            0 -> {
                Glide.with(view.context).load(R.drawable.ic_decrease).into(view)
            }

            1 -> {
                Glide.with(view.context).load(R.drawable.ic_same).into(view)
            }

            2->{
                Glide.with(view.context).load(R.drawable.ic_increase).into(view)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("setYear","setMonth")
    @JvmStatic
    fun setCalendarTitle(view : TextView, year : Int, month : Int){
        view.text="${year}년 ${month}월"
    }

}