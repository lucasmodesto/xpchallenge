package br.com.xpchallenge.testcommon

import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

fun checkIfViewShowsText(@StringRes message: Int) {
    Espresso.onView(ViewMatchers.withText(message))
        .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
}