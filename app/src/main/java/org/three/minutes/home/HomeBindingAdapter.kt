package org.three.minutes.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import org.three.minutes.R

object HomeBindingAdapter {
    @BindingAdapter("app:lottie_fileName")
    @JvmStatic
    fun loadLottie(view : LottieAnimationView, json : String){
        view.setAnimation(json)
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

}