package br.com.xpchallenge.home.router

import android.content.Context
import android.content.Intent
import br.com.xpchallenge.home.HomeActivity
import br.com.xpchallenge.router.IRoute
import br.com.xpchallenge.router.RouteData
import javax.inject.Inject

class HomeRouter @Inject constructor() : IRoute<RouteData.WithoutInput> {

    override fun open(context: Context, parameters: RouteData.WithoutInput?) {
        context.startActivity(Intent(context, HomeActivity::class.java))
    }

}