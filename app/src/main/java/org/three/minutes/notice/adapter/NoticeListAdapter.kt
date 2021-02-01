package org.three.minutes.notice.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.three.minutes.databinding.NoticeListItemBinding
import org.three.minutes.notice.data.ResponseNoticeData

class NoticeListAdapter : RecyclerView.Adapter<NoticeListAdapter.NoticeListViewHolder>() {

    // Item의 클릭 상태를 저장할 array 객체
    private var selectedItems = SparseBooleanArray()
    // 직전 클린된 Item의 Position
    private var prePosition = -1

    var dataList = listOf<ResponseNoticeData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeListViewHolder {
        val binding = NoticeListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoticeListViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: NoticeListViewHolder, position: Int) {
        holder.onBind(dataList[position],position)
    }

    inner class NoticeListViewHolder(val binding : NoticeListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun onBind(data : ResponseNoticeData, position : Int){
            binding.data = data

            changeVisibility(selectedItems.get(position))

            itemView.setOnClickListener {
                if (selectedItems.get(position)){
                    // 펼쳐진 item 클릭 시
                    selectedItems.delete(position)
                }
                else{
                    // 직전의 클릭됐던 item의 클릭 상태 해제
                    selectedItems.delete(prePosition)

                    // 새로 클릭한 item의 position 저장
                    selectedItems.put(position,true)
                }

                // 해당 포지션의 변화 알림
                if (prePosition != -1){
                    notifyItemChanged(prePosition)
                }
                notifyItemChanged(position)
                prePosition = position
            }

        }

        private fun changeVisibility(isExpanded : Boolean){
            // ValueAnimator.of int(int... values) -> View가 변할 값을 지정, int형 배열 파라미터
//            binding.noticeItemContents
//                .measure(View.MeasureSpec.UNSPECIFIED , View.MeasureSpec.UNSPECIFIED)
//
//            val dpValue = binding.noticeItemContents.measuredHeight
//            val d = itemView.context.resources.displayMetrics.density
//
//            val height = (dpValue*d).toInt()
//
//            val va = if (isExpanded) {
//                ValueAnimator.ofInt(0, dpValue)
//            } else {
//                ValueAnimator.ofInt(dpValue, 0)
//            }
//
//            va.duration = 600
//            va.addUpdateListener { animator ->
//                val value = animator.animatedValue as Int
//                binding.noticeItemContents.layoutParams.height = value
//                binding.noticeItemContents.requestLayout()
//
//
//            }
//
//            va.start()

            // 접기 펼치기 부분이 사라지는 곳
            binding.noticeItemContents.visibility = if (isExpanded) {
                View.VISIBLE
            } else {
                View.GONE
            }

        }
    }
}