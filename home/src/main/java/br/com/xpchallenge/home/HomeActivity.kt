package br.com.xpchallenge.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.xpchallenge.presentation.core.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
        if (savedInstanceState == null) {
            home_bottom_navigation?.selectedItemId = R.id.bottom_navigation_search
        }
    }

    private fun setupBottomNavigation() {
        home_bottom_navigation?.setOnNavigationItemSelectedListener {
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
    }
}
