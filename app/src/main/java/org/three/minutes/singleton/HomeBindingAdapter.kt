package org.three.minutes.singleton

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.three.minutes.R
import org.three.minutes.home.adapter.FeedRcvAdapter
import org.three.minutes.home.data.ResponseFameData
import org.three.minutes.util.LinePagerIndicatorDecoration

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
            1->{
                glideManager.load(R.raw.once).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(view)
            }
            2->{
                glideManager.load(R.raw.twice).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(view)
            }
            3->{
                glideManager.load(R.raw.three).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(view)
            }
        }
    }

    @BindingAdapter("progressImg")
    @JvmStatic
    fun loadProgressImg(view : ImageView, checkCount : String?){
        if (checkCount == null)
            return

        when(checkCount){
            "decrease" -> {
                Glide.with(view.context).load(R.drawable.ic_decrease).into(view)
            }

            "same" -> {
                Glide.with(view.context).load(R.drawable.ic_same).into(view)
            }

            "increase"->{
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

    @SuppressLint("SetTextI18n")
    @BindingAdapter("textBind")
    @JvmStatic
    fun setTextPlusZero(view : TextView, second : Int){
        if (second<10){
            view.text = "0$second"
        }
        else{
            view.text = "$second"
        }
    }

}