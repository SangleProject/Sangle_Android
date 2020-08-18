package org.three.minutes.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.three.minutes.R

object HomeBindingAdapter {
//    @BindingAdapter("app:lottie_fileName")
//    @JvmStatic
//    fun loadLottie(view : LottieAnimationView, json : String){
//        view.setAnimation(json)
//    }

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

}