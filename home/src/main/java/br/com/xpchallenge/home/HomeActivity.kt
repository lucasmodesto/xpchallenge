package br.com.xpchallenge.home

import android.os.Bundle
import br.com.xpchallenge.di.CharacterDetailRoute
import br.com.xpchallenge.router.IRoute
import br.com.xpchallenge.router.RouteData
import br.com.xpchallenge.ui.core.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    @Inject
    @CharacterDetailRoute
    lateinit var route: IRoute<RouteData.CharacterDetailData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

}