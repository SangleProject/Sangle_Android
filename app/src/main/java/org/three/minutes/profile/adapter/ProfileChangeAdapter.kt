package org.three.minutes.profile.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.three.minutes.R
import org.three.minutes.profile.data.ProfileData

class ProfileChangeAdapter(private val context: Context) :
    RecyclerView.Adapter<ProfileChangeAdapter.ProfileChangeViewHolder>() {

    var profileList = mutableListOf<ProfileData>()

    // 선택된 아이템의 인덱스를 보관하는 변수
    var checkedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileChangeViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.profile_select_item,
            parent, false
        )
        return ProfileChangeViewHolder(view)
    }

    override fun getItemCount(): Int = profileList.size
    override fun onBindViewHolder(holder: ProfileChangeViewHolder, position: Int) {
        holder.onBind(profileList[position])
    }

    override fun onBindViewHolder(
        holder: ProfileChangeViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads)
        }
        else{
            for (key in payloads){
                val payload = key.toString()
                holder.onBind(profileList[position],payload)
            }
        }

    }

    inner class ProfileChangeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profile: ImageView = itemView.findViewById(R.id.profile_img_checkbox)
        private val check: ImageView = itemView.findViewById(R.id.sample_check)

        private val selectAnimation = AnimationUtils.loadAnimation(
            itemView.context,
            R.anim.profile_select_ani
        )
        private val unSelectAnimation = AnimationUtils.loadAnimation(
            itemView.context,
            R.anim.profile_unselect_ani
        )

        fun onBind(data: ProfileData, payload : String = "") {
            Glide.with(itemView).load(data.profileImg).into(profile)

            when(payload){
                "unselected" -> {
                    itemView.startAnimation(unSelectAnimation)


                }
                "selected" ->{
                    itemView.startAnimation(selectAnimation)
                }
            }

            if (checkedPosition != adapterPosition) {
                check.visibility = View.GONE
                itemView.scaleY = 1.0f
                itemView.scaleX = 1.0f
            }
            else{
                check.visibility = View.VISIBLE
                itemView.scaleX = 1.2f
                itemView.scaleY = 1.2f
            }

            itemView.setOnClickListener {
                check.visibility = View.VISIBLE

                if (checkedPosition != adapterPosition) {
                    notifyItemChanged(checkedPosition,"unselected")
                    checkedPosition = adapterPosition
                    notifyItemChanged(adapterPosition, "selected")
                }
            }
        }
    }
}