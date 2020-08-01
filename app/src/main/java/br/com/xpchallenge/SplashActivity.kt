package br.com.xpchallenge

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import br.com.xpchallenge.di.HomeRoute
import br.com.xpchallenge.router.IRoute
import br.com.xpchallenge.router.RouteData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    @HomeRoute
    lateinit var route: IRoute<RouteData.WithoutData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            route.open(context = this)
            finish()
        }, 800)
    }
}