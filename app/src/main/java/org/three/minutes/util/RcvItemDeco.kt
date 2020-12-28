package org.three.minutes.util

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RcvItemDeco(
    context: Context,
    top: Int,
    bottom: Int,
    right: Int,
    left: Int,
    private val isGrid: Boolean = false,
    private val spanCount: Int = 0
) : RecyclerView.ItemDecoration() {
    private val sizeTop: Int
    private val sizeBottom: Int
    private val sizeRight: Int
    private val sizeLeft: Int

    init {

        sizeTop = dpToPx(context, top)
        sizeBottom = dpToPx(context, bottom)
        sizeRight = dpToPx(context, right)
        sizeLeft = dpToPx(context, left)
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (isGrid) {
            val lp = view.layoutParams as GridLayoutManager.LayoutParams

            when (lp.spanIndex) {
                0 -> { // 가장 왼쪽 아이템
                    setOutRect(
                        outRect, sizeTop / 2, sizeBottom / 2,
                        sizeRight / 2, sizeLeft
                    )
                }
                spanCount - 1 -> { // 가장 오른쪽 아이템
                    setOutRect(
                        outRect, sizeTop / 2, sizeBottom / 2,
                        sizeRight, sizeLeft / 2
                    )
                }
                else -> { // 그 외 가운데 아이템
                    setOutRect(
                        outRect, sizeTop / 2, sizeBottom / 2,
                        sizeRight / 2, sizeLeft / 2
                    )
                }

            }
        } else {
            setOutRect(outRect, sizeTop, sizeBottom, sizeRight, sizeLeft)
        }
    }

}

private fun setOutRect(outRect: Rect, top: Int, bottom: Int, right: Int, left: Int) {
    outRect.top = top
    outRect.bottom = bottom
    outRect.right = right
    outRect.left = left
}
