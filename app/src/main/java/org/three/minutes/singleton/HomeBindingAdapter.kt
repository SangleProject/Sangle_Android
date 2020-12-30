package org.three.minutes.singleton

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.three.minutes.R

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
    fun loadProgressImg(view : ImageView, checkCount : String){
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