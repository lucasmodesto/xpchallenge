package br.com.xpchallenge.ui.core

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity(), BaseView {

    override fun showLoading() {
        // TODO
    }

    override fun hideLoading() {
        // TODO
    }

    override fun showError(message: Int, retryAction: () -> Unit) {
        // TODO
    }
}