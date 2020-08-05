package br.com.xpchallenge.testcommon

import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher


fun checkIfViewShowsText(@StringRes message: Int) {
    Espresso.onView(ViewMatchers.withText(message))
        .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
}

fun atPosition(position: Int, @NonNull itemMatcher: Matcher<View?>): Matcher<View?>? {
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder: RecyclerView.ViewHolder =
                view.findViewHolderForAdapterPosition(position) ?: return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}