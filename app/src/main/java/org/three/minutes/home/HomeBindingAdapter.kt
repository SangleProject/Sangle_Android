package org.three.minutes.home

import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

object HomeBindingAdapter {
    @BindingAdapter("app:lottie_fileName")
    @JvmStatic
    fun loadLottie(view : LottieAnimationView, json : String){
        view.setAnimation(json)
    }
}