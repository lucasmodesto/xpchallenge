package br.com.xpchallenge.home.router

import android.content.Context
import android.content.Intent
import br.com.xpchallenge.home.HomeActivity
import br.com.xpchallenge.router.HomeRouteData
import br.com.xpchallenge.router.IRoute
import javax.inject.Inject

class HomeRouter @Inject constructor() : IRoute<HomeRouteData> {

    override fun open(context: Context?, parameters: HomeRouteData) {
        context?.startActivity(Intent(context, HomeActivity::class.java))
    }
}
