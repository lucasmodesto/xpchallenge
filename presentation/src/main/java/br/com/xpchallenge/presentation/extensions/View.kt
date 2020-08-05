package br.com.xpchallenge.presentation.extensions

import android.view.View

fun View.setIsVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}
