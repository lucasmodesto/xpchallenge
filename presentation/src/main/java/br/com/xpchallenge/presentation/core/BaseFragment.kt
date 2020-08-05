package br.com.xpchallenge.presentation.core

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(), BaseView {

    private val baseActivity by lazy { activity as? BaseActivity }

    override fun showError(message: Int, retryAction: () -> Unit) {
        baseActivity?.showError(message, retryAction)
    }
}