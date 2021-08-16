package org.three.minutes.architect.data.diffutil

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import org.three.minutes.architect.data.ResponseBlockedUser

class BlockedUserDiffUtil(val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {

    private val blockedUserList = mutableListOf<ResponseBlockedUser>()

    fun size(): Int = blockedUserList.size

    fun get(position: Int): ResponseBlockedUser = blockedUserList[position]

    fun getAll() = blockedUserList

    fun set(list: MutableList<ResponseBlockedUser>) {
        calcDiff(list)
        setNewList(list)
    }

    fun add(item: ResponseBlockedUser) {
        val newList = mutableListOf<ResponseBlockedUser>()
        newList.addAll(blockedUserList)
        newList.add(item)

        calcDiff(newList)
        setNewList(newList)
    }

    fun removeAt(position: Int) {
        val newList = mutableListOf<ResponseBlockedUser>()
        newList.addAll(blockedUserList)
        newList.removeAt(position)

        calcDiff(newList)
        setNewList(newList)
    }

    private fun setNewList(newList: MutableList<ResponseBlockedUser>) {
        blockedUserList.clear()
        blockedUserList.addAll(newList)
    }


    private fun calcDiff(newList: MutableList<ResponseBlockedUser>) {
        val diffUtilCallBack = BlockedUserDiffUtilCallBack(blockedUserList, newList)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtilCallBack)
        diffResult.dispatchUpdatesTo(adapter)
    }

    class BlockedUserDiffUtilCallBack(
        private val oldList: MutableList<ResponseBlockedUser>,
        private val newList: MutableList<ResponseBlockedUser>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].blockedIdx == newList[newItemPosition].blockedIdx
    }

}