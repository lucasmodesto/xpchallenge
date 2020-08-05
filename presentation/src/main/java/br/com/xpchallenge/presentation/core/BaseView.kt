package br.com.xpchallenge.presentation.core


interface BaseView {
    fun showError(message: Int, retryAction: () -> Unit)
}