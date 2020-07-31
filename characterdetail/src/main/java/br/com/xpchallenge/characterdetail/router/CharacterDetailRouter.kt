package br.com.xpchallenge.characterdetail.router

import android.content.Context
import android.content.Intent
import br.com.xpchallenge.characterdetail.CharacterDetailActivity
import br.com.xpchallenge.router.IRoute
import br.com.xpchallenge.router.RouteData
import javax.inject.Inject

class CharacterDetailRouter @Inject constructor() : IRoute<RouteData.CharacterDetailData> {

    override fun open(context: Context, parameters: RouteData.CharacterDetailData?) {
        context.startActivity(Intent(context, CharacterDetailActivity::class.java))
    }


}