package br.com.xpchallenge.presentation.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.recyclerview.widget.RecyclerView
import br.com.xpchallenge.presentation.R
import kotlin.math.roundToInt

class GridItemDecoration(
    @DimenRes private val spacing: Int = R.dimen.default_horizontal_margin,
    @IntegerRes private val gridSpanCount: Int = R.integer.grid_span_count
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spacingPx = parent.context.resources.getDimension(spacing)
        val spanCount = parent.context.resources.getInteger(gridSpanCount)
        val bit = if (spacingPx > spanCount) (spacingPx / spanCount).roundToInt() else 1

        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        outRect.top = if (itemPosition < spanCount) 0 else spacingPx.toInt()
        outRect.bottom = 0
        val rowPosition = itemPosition % spanCount
        outRect.left = rowPosition * bit
        outRect.right = (spanCount - rowPosition - 1) * bit
    }
}
