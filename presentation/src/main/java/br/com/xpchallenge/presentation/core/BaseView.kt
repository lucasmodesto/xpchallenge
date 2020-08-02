package br.com.xpchallenge.presentation.core


interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun showError(message: Int, retryAction: () -> Unit)
}