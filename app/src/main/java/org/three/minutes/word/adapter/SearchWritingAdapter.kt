package org.three.minutes.word.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.OtherWritingItemListBinding
import org.three.minutes.word.data.ResponsePastSearchData
import org.three.minutes.word.data.ResponseSearchTopicData
import org.three.minutes.word.viewholder.SearchPastWritingViewHolder
import org.three.minutes.word.viewholder.SearchResultViewHolder

class SearchWritingAdapter(
    val context: Context,
    private val isPastView: Boolean = true
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener{
        fun onPastSearchItemClick(v : View, data : ResponsePastSearchData) = Unit
        fun onSearchTopicItemClick(v : View, data : ResponseSearchTopicData) = Unit
    }
    private val TAG_PAST = 0
    private val TAG_SEARCH = 1

    private var clickListener : OnItemClickListener? = null

    var pastData = listOf<ResponsePastSearchData>()
    var resultData = listOf<ResponseSearchTopicData>()

    fun setItemClickLitener(listener : OnItemClickListener){
        clickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPastView) {
            TAG_PAST
        } else {
                TAG_SEARCH
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TAG_PAST -> {
                val binding = OtherWritingItemListBinding.inflate(
                    LayoutInflater.from(context),
                    parent, false
                )

                return SearchPastWritingViewHolder(binding)
            }
            else -> {
                val binding = OtherWritingItemListBinding.inflate(
                    LayoutInflater.from(context),
                    parent, false
                )

                return SearchResultViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int =
        if (isPastView) {
            pastData.size
        } else {
            resultData.size
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchPastWritingViewHolder -> {
                holder.onBind(pastData[position],clickListener)
            }
            is SearchResultViewHolder -> {
                holder.onBind(resultData[position],clickListener)
            }
        }
    }
}