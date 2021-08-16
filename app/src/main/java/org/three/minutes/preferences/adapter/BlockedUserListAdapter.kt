package org.three.minutes.preferences.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.three.minutes.architect.data.ResponseBlockedUser
import org.three.minutes.architect.data.diffutil.BlockedUserDiffUtil
import org.three.minutes.databinding.ItemBlockedUserBinding

class BlockedUserListAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var blockedUserDiffUtil = BlockedUserDiffUtil(this)
    private var clickListener: ClickListener? = null

    interface ClickListener {
        fun onCancelBlock(blockedUser: ResponseBlockedUser, position: Int)
    }

    fun setOnClickItem(l: ClickListener) {
        clickListener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBlockedUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BlockedUserViewHolder(binding)
    }

    override fun getItemCount(): Int = blockedUserDiffUtil.size()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BlockedUserViewHolder)?.onBind(blockedUserDiffUtil.get(position), clickListener)
    }


    class BlockedUserViewHolder(val binding: ItemBlockedUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseBlockedUser, l: ClickListener?) {
            binding.blockedUser = data

            Glide.with(itemView).load(data.profileImg).into(binding.imgProfile)

            binding.btnCancelBlock.setOnClickListener {
                l?.onCancelBlock(data, adapterPosition)
            }
        }
    }
}