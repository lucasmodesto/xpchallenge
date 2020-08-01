package br.com.xpchallenge.ui.core

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(), BaseView {

    private val baseActivity by lazy { activity as? BaseActivity }

    override fun showLoading() {
        baseActivity?.showLoading()
    }

    override fun hideLoading() {
        baseActivity?.hideLoading()
    }

    override fun showError(message: Int, retryAction: () -> Unit) {
        baseActivity?.showError(message, retryAction)
    }
}