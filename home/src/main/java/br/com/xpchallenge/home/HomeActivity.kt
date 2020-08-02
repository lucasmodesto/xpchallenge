package br.com.xpchallenge.home

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.xpchallenge.presentation.core.BaseActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
    }

    @VisibleForTesting
    fun setupBottomNavigation() {
        home_bottomnavigation?.setOnNavigationItemSelectedListener {
            val fmTransaction = supportFragmentManager.beginTransaction()
            val fragment: Fragment = when (it.itemId) {
                R.id.bottom_navigation_search -> {
                    SearchCharactersFragment()
                }

                else -> {
                    FavoriteCharactersFragment()
                }
            }
            fmTransaction
                .replace(R.id.home_fragment_container, fragment)
                .commit()
            return@setOnNavigationItemSelectedListener true
        }
        home_bottomnavigation?.selectedItemId = R.id.bottom_navigation_search
    }

    override fun showError(message: Int, retryAction: () -> Unit) {
        if (!isFinishing) {
            Snackbar.make(
                findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_INDEFINITE
            ).setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                .setAnchorView(home_bottomnavigation)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                .setAction(getString(R.string.message_retry)) {
                    retryAction.invoke()
                }.show()
        }
    }

}