package br.com.xpchallenge.presentation.core

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.xpchallenge.presentation.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity(), BaseView {

    override fun showLoading() {
        // TODO: default loading view
    }

    override fun hideLoading() {
        // TODO: default loading view
    }

    override fun showError(message: Int, retryAction: () -> Unit) {
        if (!isFinishing) {
            Snackbar.make(
                findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_INDEFINITE
            ).setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                .setAction(getString(R.string.message_retry)) {
                    retryAction.invoke()
                }.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}