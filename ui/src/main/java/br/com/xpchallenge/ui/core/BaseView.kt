package br.com.xpchallenge.ui.core


interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun showError(message: Int, retryAction: () -> Unit)
}