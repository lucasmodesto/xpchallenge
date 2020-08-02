package br.com.xpchallenge.presentation.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class VerticalSpaceItemDecoration(
        @DimenRes private val verticalSpacing: Int,
        private val applyOnLastItem: Boolean = true) : ItemDecoration() {

    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val spaceInPixels = parent.context.resources.getDimension(verticalSpacing)

        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            if (applyOnLastItem) outRect.bottom = spaceInPixels.toInt() else outRect.setEmpty()
        } else {
            outRect.bottom = spaceInPixels.toInt()
        }
    }
}