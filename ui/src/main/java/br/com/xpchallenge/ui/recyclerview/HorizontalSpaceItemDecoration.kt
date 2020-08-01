package br.com.xpchallenge.ui.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import br.com.xpchallenge.ui.R

class HorizontalSpaceItemDecoration(
    @DimenRes private val leftSpacing: Int = R.dimen.zero,
    @DimenRes private val rightSpacing: Int = R.dimen.zero
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val leftSpacingInPixels = parent.context.resources.getDimension(leftSpacing).toInt()
        val rightSpacingInPixels = parent.context.resources.getDimension(rightSpacing).toInt()

        outRect.left = leftSpacingInPixels
        outRect.right = rightSpacingInPixels
    }
}