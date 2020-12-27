package org.three.minutes.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
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
            if (payload == "unselected"){
                itemView.startAnimation(unSelectAnimation)
            }

            if (checkedPosition != adapterPosition) {

                check.visibility = View.GONE
            }


            itemView.setOnClickListener {

                check.visibility = View.VISIBLE

                if (checkedPosition != adapterPosition) {
                    itemView.startAnimation(selectAnimation)
                    notifyItemChanged(checkedPosition,"unselected")
                    checkedPosition = adapterPosition
                }
            }
        }
    }
}