package br.com.xpchallenge.home

import android.os.Bundle
import br.com.xpchallenge.home.favorites.FavoriteCharactersFragment
import br.com.xpchallenge.home.search.SearchCharactersFragment
import br.com.xpchallenge.ui.core.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
    }

    fun setupBottomNavigation() {
        home_bottomnavigation?.setOnNavigationItemSelectedListener {
            val fmTransaction = supportFragmentManager.beginTransaction()
            val fragment = when (it.itemId) {
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

}