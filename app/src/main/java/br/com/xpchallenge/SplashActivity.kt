package br.com.xpchallenge

import android.os.Bundle
import android.os.Handler
import br.com.xpchallenge.di.HomeRoute
import br.com.xpchallenge.router.IRoute
import br.com.xpchallenge.router.RouteData
import br.com.xpchallenge.ui.core.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    @Inject
    @HomeRoute
    lateinit var route: IRoute<RouteData.WithoutData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            route.open(context = this, parameters = RouteData.WithoutData)
            finish()
        }, 600)
    }
}