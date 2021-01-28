package org.three.minutes.word.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.UserListItemBinding
import org.three.minutes.word.data.ResponseUserListData
import org.three.minutes.word.viewholder.UserListViewHolder

class SearchUserListAdapter : RecyclerView.Adapter<UserListViewHolder>() {

    var resultData = listOf<ResponseUserListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding = UserListItemBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return UserListViewHolder(binding)
    }

    override fun getItemCount(): Int = resultData.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.onBind(resultData[position])
    }
}